package kr.bluevisor.robot.libs.core.platform.media.process

import android.content.Context
import android.media.AudioRecord
import android.os.SystemClock
import android.util.Log
import enn.libs.and.llog.LLog
import org.tensorflow.lite.support.audio.TensorAudio
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.audio.classifier.AudioClassifier
import org.tensorflow.lite.task.core.BaseOptions
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class TensorFlowLiteAudioClassificationHelper(
    val context: Context,
    var currentModel: String = YAMNET_MODEL,
    var classificationThreshold: Float = DISPLAY_THRESHOLD,
    var overlap: Float = DEFAULT_OVERLAP_VALUE,
    var numOfResults: Int = DEFAULT_NUM_OF_RESULTS,
    var currentDelegate: Int = 0,
    var numThreads: Int = 2
) {
    private lateinit var classifier: AudioClassifier
    private lateinit var tensorAudio: TensorAudio
    private lateinit var recorder: AudioRecord
    private lateinit var executor: ScheduledThreadPoolExecutor

    private val classifyRunnable = Runnable {
        classifyAudio()
    }
    private var listener: AudioClassificationListener? = null

    private lateinit var audioDataBuffer: FloatArray
    val audioChannelCount get() = recorder.channelCount
    val audioSampleRate get() = recorder.sampleRate
    val audioBufferSizeInFrames get() = recorder.bufferSizeInFrames
    val audioFloatBufferSize get() = AudioWavWriter.getBufferSizeInSamples(
        recorder.channelCount,
        recorder.bufferSizeInFrames,
        32, // AudioClassifier uses AudioFormat.ENCODING_PCM_FLOAT.
        Float.SIZE_BITS
    )

    init {
        initClassifier()
    }

    fun initClassifier() {
        // Set general detection options, e.g. number of used threads
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(numThreads)

        // Use the specified hardware for running the model. Default to CPU.
        // Possible to also use a GPU delegate, but this requires that the classifier be created
        // on the same thread that is using the classifier, which is outside of the scope of this
        // sample's design.
        when (currentDelegate) {
            DELEGATE_CPU -> {
                // Default
            }
            DELEGATE_NNAPI -> {
                baseOptionsBuilder.useNnapi()
            }
        }

        // Configures a set of parameters for the classifier and what results will be returned.
        val options = AudioClassifier.AudioClassifierOptions.builder()
            .setScoreThreshold(classificationThreshold)
            .setMaxResults(numOfResults)
            .setBaseOptions(baseOptionsBuilder.build())
            .build()

        try {
            // Create the classifier and required supporting objects
            classifier = AudioClassifier.createFromFileAndOptions(context, currentModel, options)
            tensorAudio = classifier.createInputTensorAudio()
            recorder = classifier.createAudioRecord()
            audioDataBuffer = FloatArray(audioFloatBufferSize)
            startAudioClassification()
        } catch (e: IllegalStateException) {
            listener?.onError(
                "Audio Classifier failed to initialize. See error logs for details"
            )

            Log.e("AudioClassification", "TFLite failed to load with error: " + e.message)
        }
    }

    fun startAudioClassification() {
        if (recorder.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
            return
        }

        recorder.startRecording()
        executor = ScheduledThreadPoolExecutor(1)

        // Each model will expect a specific audio recording length. This formula calculates that
        // length using the input buffer size and tensor format sample rate.
        // For example, YAMNET expects 0.975 second length recordings.
        // This needs to be in milliseconds to avoid the required Long value dropping decimals.
        val lengthInMilliSeconds = ((classifier.requiredInputBufferSize * 1.0f) /
                classifier.requiredTensorAudioFormat.sampleRate) * 1000

        val interval = (lengthInMilliSeconds * (1 - overlap)).toLong()

        executor.scheduleAtFixedRate(
            classifyRunnable,
            0,
            interval,
            TimeUnit.MILLISECONDS
        )
    }

    private fun classifyAudio() {
        val readResult = recorder.read(
            audioDataBuffer, 0, audioDataBuffer.size, AudioRecord.READ_NON_BLOCKING
        )
        if (readResult < 0) {
            LLog.w("readResult: $readResult.")
            return
        }

        tensorAudio.load(audioDataBuffer, 0, readResult)
        var inferenceTime = SystemClock.uptimeMillis()
        val output = classifier.classify(tensorAudio)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        listener?.onResult(output[0].categories, inferenceTime)
        listener?.onResult(output[0].categories, inferenceTime, audioDataBuffer, readResult)
    }

    fun stopAudioClassification() {
        recorder.stop()
        executor.shutdownNow()
    }

    fun setListener(listener: AudioClassificationListener) { this.listener = listener }
    fun removeListener() { this.listener = null }

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_NNAPI = 1
        const val DISPLAY_THRESHOLD = 0.3f
        const val DEFAULT_NUM_OF_RESULTS = 2
        const val DEFAULT_OVERLAP_VALUE = 0.5f
        const val YAMNET_MODEL = "yamnet.tflite"
        const val SPEECH_COMMAND_MODEL = "speech.tflite"
    }

    interface AudioClassificationListener {
        fun onError(error: String)
        fun onResult(results: List<Category>, inferenceTime: Long)

        // yamnet model category class name
        // https://github.com/tensorflow/models/blob/master/research/audioset/yamnet/yamnet_class_map.csv
        fun onResult(
            results: List<Category>, inferenceTime: Long, buffer: FloatArray, audioReadResult: Int) {}
    }
}