package kr.bluevisor.robot.highbuff_gpt_temi.app.robot

import androidx.lifecycle.AtomicReference
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import enn.libs.and.llog.LLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface GuideTemiDynamicGreeting {
    val selectedPromotionContentIndexLiveData: LiveData<Int>
    val temiLatestMessageLiveData: LiveData<String>
    val temiConversationRunningLiveData: LiveData<Boolean>
    var temiUserInteractableDistance: Double

    fun setTemiDynamicGreetingEnabled(enabled: Boolean)
    fun callTemiSpeakMessage(message: String, cached: Boolean = false): Boolean
    fun callTemiSpeakMessagesPeriodically(periodMillis: Long, provideMessage: () -> String)
    fun cancelToTemiSpeakMessages(stopSpeakingImmediately: Boolean = false)
    fun dismissTemiDynamicGreeting(cancelCurrentCoroutine: Boolean = true)
}

class GuideTemiDynamicGreetingDelegator(
    temiRobot: TemiRobot,
    thisCoroutineContext: CoroutineContext = EmptyCoroutineContext
) : GuideTemiDynamicGreeting {
    private val temiRobotGreetingDelegator =
        TemiRobotDynamicGreetingDelegator(temiRobot, thisCoroutineContext)

    private val thisCoroutineScope = CoroutineScope(thisCoroutineContext)

    private val personNearbyComment = AtomicReference<String?>()
    private var personFarawayComment = AtomicReference<String?>()

    private val _selectedPromotionContentIndexLiveData = MutableLiveData(-1)
    override val selectedPromotionContentIndexLiveData: LiveData<Int> =
        _selectedPromotionContentIndexLiveData

    override val temiLatestMessageLiveData =
        temiRobotGreetingDelegator.latestMessageFlow.asLiveData(thisCoroutineContext)

    override val temiConversationRunningLiveData =
        temiRobot.conversationRunningFlow.asLiveData(thisCoroutineContext)

    override var temiUserInteractableDistance
        get() = temiRobotGreetingDelegator.userInteractableDistance
        set(value) { temiRobotGreetingDelegator.userInteractableDistance = value }

    override fun setTemiDynamicGreetingEnabled(enabled: Boolean) =
        temiRobotGreetingDelegator.setDynamicGreetingEnabled(
            enabled,
            onUserIsDetected = {
                temiRobotGreetingDelegator.speakMessagesPeriodically(SPEAKING_PERIOD_MILLIS) {
                    _selectedPromotionContentIndexLiveData.postValue(-1)
                    personFarawayComment.get() ?: ""
                }
                LLog.v("onUserIsDetected is called.")
            },
            onUserIsInInteractableDistance = {
                temiRobotGreetingDelegator.speakMessagesPeriodically(SPEAKING_PERIOD_MILLIS) {
                    _selectedPromotionContentIndexLiveData.postValue(0)
                    personNearbyComment.get() ?: ""
                }
                LLog.v("onUserIsInteractableDistance is called.")
            }
        )

    override fun callTemiSpeakMessage(message: String, cached: Boolean): Boolean =
        temiRobotGreetingDelegator.speakMessage(message, cached)

    override fun callTemiSpeakMessagesPeriodically(
        periodMillis: Long, provideMessage: () -> String
    ) = temiRobotGreetingDelegator.speakMessagesPeriodically(periodMillis, provideMessage)

    override fun cancelToTemiSpeakMessages(stopSpeakingImmediately: Boolean) =
        temiRobotGreetingDelegator.cancelMessagesSpeaking(stopSpeakingImmediately)

    override fun dismissTemiDynamicGreeting(cancelCurrentCoroutine: Boolean) {
        if (cancelCurrentCoroutine) {
            thisCoroutineScope.cancel()
        }
        temiRobotGreetingDelegator.dismissDynamicGreeting(cancelCurrentCoroutine)
        LLog.v("cancelCurrentCoroutine: $cancelCurrentCoroutine.")
    }

    companion object {
        private const val SPEAKING_PERIOD_MILLIS = 4000L
    }
}