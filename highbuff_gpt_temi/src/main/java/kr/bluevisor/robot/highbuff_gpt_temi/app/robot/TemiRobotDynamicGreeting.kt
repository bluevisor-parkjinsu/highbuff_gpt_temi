package kr.bluevisor.robot.highbuff_gpt_temi.app.robot

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AtomicReference
import com.robotemi.sdk.TtsRequest
import enn.libs.and.llog.LLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobotDetection
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface TemiRobotDynamicGreeting {
    val latestMessageFlow: StateFlow<String>
    val userIsInInteractableDistanceFlow: StateFlow<Boolean>
    var userInteractableDistance: Double

    fun setDynamicGreetingEnabled(
        enabled: Boolean,
        onUserIsDetected: () -> Unit,
        onUserIsInInteractableDistance: () -> Unit
    )
    fun speakMessage(message: String, cached: Boolean = false): Boolean
    fun speakMessagesPeriodically(periodMillis: Long, provideMessage: () -> String)
    fun cancelMessagesSpeaking(stopSpeakingImmediately: Boolean = false)
    fun dismissDynamicGreeting(cancelCurrentCoroutine: Boolean = true)
}

class TemiRobotDynamicGreetingDelegator (
    private val temiRobot: TemiRobot,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
) : TemiRobotDynamicGreeting {
    private val _latestMessageFlow = MutableStateFlow("")
    override val latestMessageFlow: StateFlow<String> = _latestMessageFlow.asStateFlow()
    override val userIsInInteractableDistanceFlow: StateFlow<Boolean>
    override var userInteractableDistance = DEFAULT_USER_INTERACTABLE_DISTANCE

    private val thisCoroutineScope = CoroutineScope(coroutineContext)
    private val mainHandler = Handler(Looper.getMainLooper())

    private var dynamicGreetingJob: Job? = null
    private var periodicSpeakingJob: Job? = null
    private var speakingTaskReference = AtomicReference<Runnable?>()

    init {
        userIsInInteractableDistanceFlow = temiRobot.detectionDataFlow
            .map { detectionData -> detectionData.distance <= DEFAULT_USER_INTERACTABLE_DISTANCE }
            .stateIn(thisCoroutineScope, SharingStarted.Eagerly, false)
    }

    override fun setDynamicGreetingEnabled(
        enabled: Boolean,
        onUserIsDetected: () -> Unit,
        onUserIsInInteractableDistance: () -> Unit
    ) {
        cancelMessagesSpeaking()
        dynamicGreetingJob?.cancel()
        if (!enabled) {
            LLog.v("enabled is false. The job is cancelled.")
            return
        }

        dynamicGreetingJob = thisCoroutineScope.launch(Dispatchers.IO) {
            temiRobot.detectionStateFlow
                .combine(userIsInInteractableDistanceFlow, ::Pair)
                .collect { (state, userIsInInteractableDistance) ->
                    cancelMessagesSpeaking()

                    if (state != TemiRobotDetection.State.DETECTED) {
                        LLog.v("state is not DETECTED.")
                        return@collect
                    }
                    if (!userIsInInteractableDistance) {
                        onUserIsDetected()
                        LLog.v("userIsInInteractableDistance is false.")
                        return@collect
                    }

                    onUserIsInInteractableDistance()
                    LLog.v("state: $state," +
                            " userIsInInteractableDistance: $userIsInInteractableDistance.")
                }
        }
        LLog.v("enabled: $enabled.")
    }

    override fun speakMessage(message: String, cached: Boolean): Boolean {
        if (message.isBlank()) run {
            LLog.v("message is blank: Your speaking will be not called.")
            return false
        }
        if (temiRobot.conversationRunning) run {
            LLog.v("conversation is running: Your speaking will be not called.")
            return false
        }

        _latestMessageFlow.value = message
        temiRobot.speak(
            TtsRequest.create(
                speech = message,
                isShowOnConversationLayer = false,
                showAnimationOnly = false,
                cached = cached
            )
        )
        LLog.v("message: $message, cached: $cached.")
        return true
    }

    override fun speakMessagesPeriodically(periodMillis: Long, provideMessage: () -> String) {
        cancelMessagesSpeaking()

        speakingTaskReference.set(object : Runnable {
            override fun run() {
                val trySpeakingResult = speakMessage(provideMessage(), true)
                if (!trySpeakingResult) {
                    mainHandler.removeCallbacks(this)
                    mainHandler.postDelayed(this, periodMillis)
                }
                LLog.v("trySpeakingResult: $trySpeakingResult.")
            }
        })

        periodicSpeakingJob = thisCoroutineScope.launch(Dispatchers.IO) {
            temiRobot.conversationRunningFlow.stateIn(this).collect { running ->
                if (running) return@collect
                speakingTaskReference.get()?.let {
                    mainHandler.removeCallbacks(it)
                    mainHandler.postDelayed(it, periodMillis)
                }
            }
        }

        speakingTaskReference.get()?.run()
        LLog.v("periodMillis: $periodMillis.")
    }

    override fun cancelMessagesSpeaking(stopSpeakingImmediately: Boolean) {
        if (stopSpeakingImmediately) {
            temiRobot.cancelAllTtsRequests()
            temiRobot.finishConversation()
        }
        periodicSpeakingJob?.cancel()
        speakingTaskReference.get()?.let { mainHandler.removeCallbacks(it) }
        LLog.v("stopSpeakingImmediately: $stopSpeakingImmediately.")
    }

    override fun dismissDynamicGreeting(cancelCurrentCoroutine: Boolean) {
        temiRobot.clearAllListeners(this)
        mainHandler.removeCallbacksAndMessages(null)

        if (cancelCurrentCoroutine) {
            thisCoroutineScope.cancel()
        } else {
            dynamicGreetingJob?.cancel()
            periodicSpeakingJob?.cancel()
        }
        LLog.v("cancelCurrentCoroutine: $cancelCurrentCoroutine.")
    }

    companion object {
        private const val DEFAULT_USER_INTERACTABLE_DISTANCE = 1.0
    }
}