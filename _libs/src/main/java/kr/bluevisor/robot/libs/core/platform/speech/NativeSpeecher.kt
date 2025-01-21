package kr.bluevisor.robot.libs.core.platform.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import enn.libs.and.llog.LLog

class NativeSpeecher(
    context: Context,
    afterInitializingCallback: (() -> Unit)? = null
) : BaseSpeecher() {
    private val textToSpeech: TextToSpeech
    private val speechRecognizer: SpeechRecognizer

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.ERROR) {
                LLog.w("Initializing of TextToSpeech is failed.")
            } else {
                afterInitializingCallback?.invoke()
            }
        }.apply {
            language = getCurrentLocale(context)
            setOnUtteranceProgressListener(newUtteranceProgressListener())
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(newSpeechRecognitionListener(this@NativeSpeecher))
        }
    }

    override fun startTextToSpeech(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, DEFAULT_UTTERANCE_ID)
        LLog.v("text: $text.")
    }

    override fun stopTextToSpeech() {
        textToSpeech.stop()
        LLog.v()
    }

    override fun startSpeechToText() {
        stopTextToSpeech()
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer.startListening(intent)
        LLog.v()
    }

    override fun stopSpeechToText() {
        speechRecognizer.stopListening()
        LLog.v()
    }

    override fun finish() {
        textToSpeech.shutdown()
        speechRecognizer.destroy()
        super.finish()
        LLog.v()
    }

    private fun newUtteranceProgressListener() = object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
            onBeginTextToSpeech()
        }

        override fun onDone(utteranceId: String?) {
            onEndTextToSpeech()
            startSpeechToText()
            LLog.v("utteranceId: $utteranceId.")
        }

        @Deprecated("Deprecated in Java")
        override fun onError(utteranceId: String?) {
            onEndTextToSpeech()
            LLog.w("utteranceId: $utteranceId.")
        }
    }

    private fun newSpeechRecognitionListener(
        runningEventListener: Speecher.OnRunningEventListener
    ) = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            onBeginSpeechToText()
            LLog.v()
        }

        override fun onBeginningOfSpeech() {
        }

        override fun onRmsChanged(rmsdB: Float) {
        }

        override fun onBufferReceived(buffer: ByteArray?) {
        }

        override fun onEndOfSpeech() {
            onEndSpeechToText()
            LLog.v()
        }

        override fun onError(error: Int) {
            onEndSpeechToText()
            LLog.w("error: $error.")
        }

        override fun onResults(results: Bundle?) {
            val text = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.reduce { accumulator, text -> "$accumulator $text" }
                ?.also { resultText ->
                    with(runningEventListener) {
                        onReceivingSpeechToText(resultText)
                        onReceivedSpeechToText(resultText)
                    }
                }
            LLog.v("text: $text.")
        }

        override fun onPartialResults(partialResults: Bundle?) {
            val text = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.reduce { accumulator, text -> "$accumulator $text" }
                ?.also { partialResultText ->
                    runningEventListener.onReceivingSpeechToText(partialResultText)
                }
            LLog.v("text: $text.")
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
        }
    }

    companion object {
        private val DEFAULT_UTTERANCE_ID =
            "${NativeSpeecher::class.java.name}.default_utterance_id"
    }
}