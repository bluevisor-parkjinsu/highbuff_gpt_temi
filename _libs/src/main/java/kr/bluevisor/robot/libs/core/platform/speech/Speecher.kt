package kr.bluevisor.robot.libs.core.platform.speech

interface Speecher {
    fun startTextToSpeech(text: String)
    fun stopTextToSpeech()
    fun startSpeechToText()
    fun stopSpeechToText()

    fun setOnLifecycleEventListener(listener: OnLifecycleEventListener)
    fun removeOnLifecycleEventListener()
    fun setOnRunningEventListener(listener: OnRunningEventListener)
    fun removeOnRunningEventListener()
    fun finish()

    interface OnLifecycleEventListener {
        fun onBeginTextToSpeech()
        fun onEndTextToSpeech()
        fun onBeginSpeechToText()
        fun onEndSpeechToText()
    }

    interface OnRunningEventListener {
        fun onReceivingSpeechToText(partialText: String)
        fun onReceivedSpeechToText(text: String)
    }
}