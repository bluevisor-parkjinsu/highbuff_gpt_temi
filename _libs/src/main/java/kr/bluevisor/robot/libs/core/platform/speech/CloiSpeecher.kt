//package kr.bluevisor.robot.libs.core.platform.speech
//
//import android.content.Context
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.os.Message
//import android.util.Log
//import com.lge.thirdpartylib.STTVoiceInfo
//import com.lge.thirdpartylib.TTSVoiceInfo
//import com.lge.thirdpartylib.ThirdPartyLib
//import com.lge.thirdpartylib.ThirdPartyVoiceServiceConnector
//import com.lge.thirdpartylib.listener.ServiceConnectionListener
//import com.lge.thirdpartylib.model.AudioSampleRate
//import com.lge.thirdpartylib.model.ThirdPartyEvent
//import com.lge.thirdpartylib.model.VoiceCountryCode
//import com.lge.thirdpartylib.model.VoiceResult
//import com.lge.thirdpartylib.model.VoiceResultType
//import com.lge.thirdpartylib.util.GSON
//import enn.libs.and.llog.LLog
//import java.util.LinkedList
//
//class CloiSpeecher(
//    private val context: Context,
//    afterInitializingCallback: (() -> Unit)? = null
//) : BaseSpeecher() {
//    private var isTtsAudioPlaying = false
//    private val ttsPendingTextList: MutableList<String> = LinkedList()
//
//    init {
//        ThirdPartyVoiceServiceConnector.initService(
//            context,
//            object : ServiceConnectionListener {
//                override fun onConnected() {
//                    ThirdPartyVoiceServiceConnector.registerClient(
//                        ApiMessageHandler(this@CloiSpeecher)
//                    )
//                    afterInitializingCallback?.invoke()
//                    LLog.v("on ThirdPartyVoiceServiceConnector.")
//                }
//
//                override fun onDisconnected() {
//                    LLog.v("on ThirdPartyVoiceServiceConnector.")
//                }
//            }
//        )
//    }
//
//    override fun startTextToSpeech(text: String) {
//        ttsPendingTextList.clear()
//        text.split(TEXT_DELIMITER)
//            .fold(mutableListOf<String>()) { acc, element ->
//                acc.lastOrNull()?.let {
//                    if (it.length < PER_TEXT_LENGTH) {
//                        acc.set(acc.lastIndex, "$it$TEXT_DELIMITER$element")
//                    } else {
//                        acc.add(element)
//                    }
//                } ?: acc.add(element)
//                acc
//            }
//            .forEach() { ttsPendingTextList.add(it) }
//
//        startTextToSpeechDirectly(ttsPendingTextList.removeFirst())
//        LLog.v("text: $text, ttsPendingTextList.size: ${ttsPendingTextList.size}.")
//    }
//
//    override fun stopTextToSpeech() {
//        ttsPendingTextList.clear()
//        ThirdPartyLib.requestStopAudioTTS(context)
//        LLog.v()
//    }
//
//    override fun startSpeechToText() {
//        stopTextToSpeech()
//        val sttVoiceInfo = STTVoiceInfo(countrycode = VoiceCountryCode.KR)
//        ThirdPartyLib.requestSendAudioSTT(context, sttVoiceInfo)
//        LLog.v()
//    }
//
//    override fun stopSpeechToText() {
//        ThirdPartyLib.requestStopAudioSTT(context)
//        LLog.v()
//    }
//
//    override fun finish() {
//        isTtsAudioPlaying = false
//        ttsPendingTextList.clear()
//        ThirdPartyVoiceServiceConnector.finishService(context)
//        super.finish()
//        LLog.v()
//    }
//
//    fun onReceiveTextToSpeechResultMessage(result: String?) {
//        val voiceResult = GSON.fromJson(result, VoiceResult::class.java)
//        val voiceResultType = voiceResult.resultType ?: run {
//            // We need to definite this! voiceResultType is nullable.
//            val resultContainsMakeFileDownToken = result?.contains("MAKE_FILE_DONE") ?: false
//            LLog.print(
//                if (resultContainsMakeFileDownToken) Log.VERBOSE else Log.WARN,
//                "voiceResultType is null: voiceResult: $voiceResult, result: $result," +
//                        " resultContainsMakeFileDownToken: $resultContainsMakeFileDownToken."
//            )
//            return
//        }
//
//        when (voiceResultType) {
//            VoiceResultType.START -> {
//                isTtsAudioPlaying = true
//                onBeginTextToSpeech()
//            }
//            VoiceResultType.COMPLETE -> run {
//                if (!isTtsAudioPlaying) return@run
//                isTtsAudioPlaying = false
//
//                if (ttsPendingTextList.isNotEmpty()) {
//                    startTextToSpeechDirectly(ttsPendingTextList.removeFirst())
//                    LLog.v("ttsPendingTextList.size: ${ttsPendingTextList.size}.")
//                } else {
//                    onEndTextToSpeech()
//                    startSpeechToText()
//                }
//            }
//            VoiceResultType.ERROR -> {
//                onEndTextToSpeech()
//                LLog.w("Error occurred.")
//            }
//            else -> {}
//        }
//        LLog.v("voiceResultType: $voiceResultType, voiceResult: $voiceResult, result: $result.")
//    }
//
//    fun onReceiveSpeechToTextResultMessage(result: String?) {
//        val voiceResult = GSON.fromJson(result, VoiceResult::class.java)
//        val voiceResultType = voiceResult.resultType ?: run {
//            // We need to definite this! voiceResultType is nullable.
//            LLog.w("voiceResultType is null: voiceResult: $voiceResult, result: $result.")
//            return
//        }
//
//        when (voiceResultType) {
//            VoiceResultType.START -> { onBeginSpeechToText() }
//            VoiceResultType.COMPLETE -> { onEndSpeechToText() }
//            VoiceResultType.RESPONSE -> {
//                voiceResult.text?.let {
//                    onReceivingSpeechToText(it)
//                    if (voiceResult.isFinal) {
//                        onReceivedSpeechToText(it)
//                    }
//                }
//            }
//            VoiceResultType.ERROR -> {
//                onEndSpeechToText()
//                LLog.w("Error occurred.")
//            }
//            else -> {}
//        }
//        LLog.v("voiceResultType: $voiceResultType, voiceResult: $voiceResult, result: $result.")
//    }
//
//    private fun startTextToSpeechDirectly(text: String) {
//        val ttsVoiceInfo = TTSVoiceInfo(
//            speechtext = text,
//            countrycode = VoiceCountryCode.KR,
//            voiceType = "ko-KR-Neural2-C",
//            pitch = 0.0,
//            speechRate = 1.0,
//            isLoop = false,
//            mp3Encoding = false,
//            sampleRate = AudioSampleRate.ASR_32Hz
//        )
//
//        ThirdPartyLib.requestStopAudioTTS(context)
//        ThirdPartyLib.requestSendAudioTTS(context, ttsVoiceInfo)
//        LLog.v("text: $text.")
//    }
//
//    class ApiMessageHandler(private val speecher: CloiSpeecher) : Handler(Looper.getMainLooper()) {
//        override fun handleMessage(msg: Message) {
//            val thirdPartyEvent = ThirdPartyEvent.fromValue(msg.what)
//            val messageInfo = msg.obj as Bundle
//            val result = messageInfo.getString("param")
//
//            when (thirdPartyEvent) {
//                ThirdPartyEvent.SEND_TTS_RESULT ->
//                    speecher.onReceiveTextToSpeechResultMessage(result)
//                ThirdPartyEvent.SEND_STT_RESULT ->
//                    speecher.onReceiveSpeechToTextResultMessage(result)
//                else -> {}
//            }
//            LLog.v(
//                "thirdPartyEvent: ${thirdPartyEvent}, messageInfo: $messageInfo," +
//                        " result: $result."
//            )
//        }
//    }
//
//    companion object {
//        private const val TEXT_DELIMITER = "\n\n"
//        private const val PER_TEXT_LENGTH = 256;
//    }
//}