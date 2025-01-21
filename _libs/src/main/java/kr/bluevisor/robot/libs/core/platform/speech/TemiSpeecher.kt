package kr.bluevisor.robot.libs.core.platform.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.robotemi.sdk.Robot
import com.robotemi.sdk.SttLanguage
import com.robotemi.sdk.TtsRequest
import enn.libs.and.llog.LLog

class TemiSpeecher(
    context: Context,
    afterInitializingCallback: (() -> Unit)? = null
) : BaseSpeecher() {
    private val robot = Robot.getInstance()
    private val textToSpeech: TextToSpeech

    private val temiTtsListener = newTemiTtsListener()
    private val temiAsrListener = newTemiAsrListener()

    init {
        robot.apply {
            addTtsListener(temiTtsListener)
            addAsrListener(temiAsrListener)
        }

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
        robot.wakeup()
        LLog.v()
    }

    override fun stopSpeechToText() {
        robot.finishConversation()
        LLog.v()
    }

    override fun finish() {
        robot.apply {
            removeTtsListener(temiTtsListener)
            removeAsrListener(temiAsrListener)
        }
        textToSpeech.shutdown()
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

    private fun newTemiTtsListener() = object : Robot.TtsListener {
        override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
            when (ttsRequest.status) {
                TtsRequest.Status.PROCESSING -> {}
                TtsRequest.Status.STARTED -> { onBeginTextToSpeech() }
                TtsRequest.Status.COMPLETED -> { onEndTextToSpeech() }
                TtsRequest.Status.NOT_ALLOWED -> { LLog.w("Status: Not allowed.")}
                TtsRequest.Status.PENDING -> { LLog.v("Status: Pending.") }
                TtsRequest.Status.CANCELED -> { onEndTextToSpeech() }
                TtsRequest.Status.ERROR -> { LLog.w("Status: Error.") }
            }
            LLog.v("ttsRequest: $ttsRequest.")
        }
    }

    private fun newTemiAsrListener() = object : Robot.AsrListener {
        override fun onAsrResult(asrResult: String, sttLanguage: SttLanguage) {
            robot.finishConversation()
            onReceivedSpeechToText(asrResult)
            LLog.v("asrResult: $asrResult, sttLanguage: $sttLanguage.")
        }
    }

    companion object {
        private val DEFAULT_UTTERANCE_ID =
            "${TemiSpeecher::class.java.name}.default_utterance_id"
    }
}