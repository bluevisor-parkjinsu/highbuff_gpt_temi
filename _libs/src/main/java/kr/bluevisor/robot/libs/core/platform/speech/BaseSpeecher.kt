package kr.bluevisor.robot.libs.core.platform.speech

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import enn.libs.and.llog.LLog
import java.util.Locale

abstract class BaseSpeecher
: Speecher, Speecher.OnLifecycleEventListener, Speecher.OnRunningEventListener
{
    protected val mainHandler = Handler(Looper.getMainLooper())
    protected var lifecycleEventListener: Speecher.OnLifecycleEventListener? = null
    protected var runningEventListener: Speecher.OnRunningEventListener? = null

    override fun finish() {
        lifecycleEventListener = null
        runningEventListener = null
    }

    override fun onBeginTextToSpeech() {
        mainHandler.post {
            lifecycleEventListener?.onBeginTextToSpeech()
        }
        LLog.v()
    }

    override fun onEndTextToSpeech() {
        mainHandler.post {
            lifecycleEventListener?.onEndTextToSpeech()
        }
        LLog.v()
    }

    override fun onBeginSpeechToText() {
        mainHandler.post {
            lifecycleEventListener?.onBeginSpeechToText()
        }
        LLog.v()
    }

    override fun onEndSpeechToText() {
        mainHandler.post {
            lifecycleEventListener?.onEndSpeechToText()
        }
        LLog.v()
    }

    override fun onReceivingSpeechToText(partialText: String) {
        mainHandler.post {
            runningEventListener?.onReceivingSpeechToText(partialText)
        }
        LLog.v("partialText: $partialText.")
    }

    override fun onReceivedSpeechToText(text: String) {
        mainHandler.post {
            runningEventListener?.onReceivedSpeechToText(text)
        }
        LLog.v("text: $text.")
    }

    override fun setOnLifecycleEventListener(listener: Speecher.OnLifecycleEventListener) {
        lifecycleEventListener = listener
        LLog.v()
    }

    override fun removeOnLifecycleEventListener() {
        lifecycleEventListener = null
        LLog.v()
    }

    override fun setOnRunningEventListener(listener: Speecher.OnRunningEventListener) {
        runningEventListener = listener
        LLog.v()
    }

    override fun removeOnRunningEventListener() {
        runningEventListener = null
        LLog.v()
    }

    companion object {
        @JvmStatic
        protected fun getCurrentLocale(context: Context): Locale {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales[0]
            } else {
                @Suppress("DEPRECATION")
                context.resources.configuration.locale
            }
        }
    }
}