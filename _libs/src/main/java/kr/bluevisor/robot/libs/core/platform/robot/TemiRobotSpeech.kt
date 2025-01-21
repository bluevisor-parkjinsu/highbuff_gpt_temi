package kr.bluevisor.robot.libs.core.platform.robot

import com.robotemi.sdk.Robot
import com.robotemi.sdk.SttLanguage
import com.robotemi.sdk.SttRequest
import com.robotemi.sdk.TtsRequest
import enn.libs.and.llog.LLog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import java.util.WeakHashMap

interface TemiRobotSpeech {
    val ttsStatusFlow: StateFlow<TtsRequest.Status>
    val conversationViewAttachedFlow: StateFlow<Boolean>
    val conversationRunning: Boolean
    val conversationRunningFlow: Flow<Boolean>

    fun speak(ttsRequest: TtsRequest)
    fun speakSimple(message: String)
    fun askQuestion(ttsRequest: TtsRequest)
    fun askQuestionSimple(message: String)
    fun wakeUp(sttRequest: SttRequest)
    fun wakeUpFor(languages: List<SttLanguage> = emptyList())
    fun cancelAllTtsRequests()
    fun finishConversation()
    fun speakFlow(ttsRequest: TtsRequest): Flow<TtsRequest>
    fun speakSimpleFlow(message: String): Flow<TtsRequest>
    fun askQuestionFlow(ttsRequest: TtsRequest): Flow<Map<String, Any>>
    fun askQuestionSimpleFlow(message: String): Flow<Map<String, Any>>
    fun wakeUpFlow(isOneShot: Boolean = true): Flow<Map<String, Any>>

    fun registerPrivateSpeechObservers()
    fun unregisterPrivateSpeechObservers()
    fun clearAllSpeechListeners(parent: Any? = null)
    fun addTtsListener(parent: Any, listener: Robot.TtsListener)
    fun removeTtsListener(parent: Any, listener: Robot.TtsListener)
    fun clearAllTtsListeners(parent: Any)
    fun addAsrListener(parent: Any, listener: Robot.AsrListener)
    fun removeAsrListener(parent: Any, listener: Robot.AsrListener)
    fun clearAllAsrListeners(parent: Any)
    fun addConversationViewAttachesListener(
        parent: Any, listener: Robot.ConversationViewAttachesListener)
    fun removeConversationViewAttachesListener(
        parent: Any, listener: Robot.ConversationViewAttachesListener)
    fun clearAllConversationViewAttachesListeners(parent: Any)

    companion object {
        const val ASR_LISTENER__ASR_RESULT = "asrResult"

        val TTS_STATUS__RUNNING__SET = setOf(
            TtsRequest.Status.PENDING,
            TtsRequest.Status.PROCESSING,
            TtsRequest.Status.STARTED
        )
        val TTS_STATUS__COMPLETED__SET = setOf(
            TtsRequest.Status.COMPLETED,
            TtsRequest.Status.CANCELED
        )
        val TTS_STATUS__INCOMPLETE__SET = setOf(
            TtsRequest.Status.ERROR,
            TtsRequest.Status.NOT_ALLOWED
        )
    }
}

class TemiRobotSpeechDelegator(private val core: Robot) : TemiRobotSpeech {
    private val _ttsStatusFlow = MutableStateFlow(TtsRequest.Status.COMPLETED)
    override val ttsStatusFlow = _ttsStatusFlow.asStateFlow()

    private val _conversationViewAttachedFlow = MutableStateFlow(false)
    override val conversationViewAttachedFlow = _conversationViewAttachedFlow.asStateFlow()

    private val calcConversationRunning = {
        ttsStatus: TtsRequest.Status, conversationViewAttached: Boolean ->
        ttsStatus in TemiRobotSpeech.TTS_STATUS__RUNNING__SET || conversationViewAttached
    }
    override val conversationRunning
        get() = calcConversationRunning(_ttsStatusFlow.value, _conversationViewAttachedFlow.value)
    override val conversationRunningFlow: Flow<Boolean> =
        combine(_ttsStatusFlow, _conversationViewAttachedFlow, calcConversationRunning)

    private val privateTtsListener =
        newPrivateTtsListener()
    private val ttsListenerMap =
        WeakHashMap<Any, MutableSet<Robot.TtsListener>>()

    private val privateAsrListener =
        newPrivateAsrListener()
    private val asrListenerMap =
        WeakHashMap<Any, MutableSet<Robot.AsrListener>>()

    private val privateConversationViewAttachesListener =
        newPrivateConversationViewAttachesListener()
    private val conversationViewAttachesListenerMap =
        WeakHashMap<Any, MutableSet<Robot.ConversationViewAttachesListener>>()

    private fun newPrivateTtsListener()
    = object : Robot.TtsListener {
        override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
            _ttsStatusFlow.value = ttsRequest.status
            ttsListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onTtsStatusChanged(ttsRequest) }
            }
            LLog.v("ttsRequest: $ttsRequest.")
        }
    }

    private fun newPrivateAsrListener()
    = object : Robot.AsrListener {
        override fun onAsrResult(asrResult: String, sttLanguage: SttLanguage) {
            asrListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onAsrResult(asrResult, sttLanguage) }
            }
            LLog.v("asrResult: $asrResult, sttLanguage: $sttLanguage.")
        }
    }

    private fun newPrivateConversationViewAttachesListener()
    = object : Robot.ConversationViewAttachesListener {
        override fun onConversationAttaches(isAttached: Boolean) {
            _conversationViewAttachedFlow.value = isAttached
            conversationViewAttachesListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onConversationAttaches(isAttached) }
            }
            LLog.v("isAttached: $isAttached.")
        }
    }

    override fun speak(ttsRequest: TtsRequest) = core.speak(ttsRequest)

    override fun speakSimple(message: String) = core.speak(TtsRequest.create(message))

    override fun askQuestion(ttsRequest: TtsRequest) = core.askQuestion(ttsRequest)

    override fun askQuestionSimple(message: String) = core.askQuestion(message)

    override fun wakeUp(sttRequest: SttRequest) = core.wakeup(sttRequest)

    override fun wakeUpFor(languages: List<SttLanguage>) = core.wakeup(languages)

    override fun cancelAllTtsRequests() = core.cancelAllTtsRequests()

    override fun finishConversation() = core.finishConversation()

    override fun speakFlow(ttsRequest: TtsRequest) = callbackFlow {
        if (ttsRequest.speech.isBlank()) {
            val errorMessage = "ttsRequest.speech is blank: ttsRequest: $ttsRequest."
            LLog.w(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }

        val ttsListener = object : Robot.TtsListener {
            override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
                @OptIn(DelicateCoroutinesApi::class)
                val isClosed = isClosedForSend

                if (isClosed) {
                    core.removeTtsListener(this)
                    LLog.v("Closed callbackFlow try to emit: ttsRequest: $ttsRequest.")
                    return
                } else if (trySend(ttsRequest).isFailure) {
                    LLog.w("trySend() is failed.")
                }

                when (val status = ttsRequest.status) {
                    TtsRequest.Status.PENDING,
                    TtsRequest.Status.PROCESSING,
                    TtsRequest.Status.STARTED -> {}
                    TtsRequest.Status.COMPLETED,
                    TtsRequest.Status.CANCELED -> {
                        core.removeTtsListener(this)
                        close()
                        LLog.v("close when status is $status.")
                    }
                    TtsRequest.Status.ERROR,
                    TtsRequest.Status.NOT_ALLOWED -> {
                        core.removeTtsListener(this)
                        close()
                        LLog.w("close when status is $status.")
                    }
                }
                LLog.v("ttsRequest: $ttsRequest.")
            }
        }

        core.addTtsListener(ttsListener)
        core.speak(ttsRequest)

        LLog.v("ttsRequest: $ttsRequest.")
        awaitClose {
            core.removeTtsListener(ttsListener)
            LLog.v("awaitClose() called: ttsRequest: $ttsRequest.")
        }
    }

    override fun speakSimpleFlow(message: String): Flow<TtsRequest> =
        // FIXME : Restore me.
        // speakFlow(TtsRequest.create(message))
        speakFlow(TtsRequest.create(
            speech = message,
            isShowOnConversationLayer = true,
            showAnimationOnly = true
        ))

    override fun askQuestionFlow(ttsRequest: TtsRequest) = callbackFlow {
        if (ttsRequest.speech.isBlank()) {
            val errorMessage = "ttsRequest.speech is blank: ttsRequest: $ttsRequest."
            LLog.w(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }

        val asrListener = object : Robot.AsrListener {
            override fun onAsrResult(asrResult: String, sttLanguage: SttLanguage) {
                core.finishConversation()

                val parameterMap = mapOf(
                    "asrResult" to asrResult,
                    "sttLanguage" to sttLanguage
                )

                @OptIn(DelicateCoroutinesApi::class)
                val isClosed = isClosedForSend

                if (isClosed) {
                    core.removeAsrListener(this)
                    LLog.v("Closed callbackFlow try to emit: parameterMap: $parameterMap," +
                            " ttsRequest: $ttsRequest.")
                    return
                } else if (trySend(parameterMap).isFailure) {
                    LLog.w("trySend() is failed.")
                }

                core.removeAsrListener(this)
                close()
                LLog.v("parameterMap: $parameterMap, ttsRequest: $ttsRequest.")
            }
        }
        val conversationViewAttachesListener = object : Robot.ConversationViewAttachesListener {
            override fun onConversationAttaches(isAttached: Boolean) {
                @OptIn(DelicateCoroutinesApi::class)
                val isClosed = isClosedForSend

                if (isClosed) {
                    core.removeConversationViewAttachesListener(this)
                    LLog.v("Closed callbackFlow try to emit: ttsRequest: $ttsRequest.")
                    return
                } else if (!isAttached) {
                    core.removeConversationViewAttachesListener(this)
                    close()
                }
                LLog.v("isAttached: $isAttached.")
            }
        }

        core.addAsrListener(asrListener)
        core.addConversationViewAttachesListener(conversationViewAttachesListener)
        core.askQuestion(ttsRequest)

        LLog.v("ttsRequest: $ttsRequest.")
        awaitClose {
            core.removeAsrListener(asrListener)
            core.removeConversationViewAttachesListener(conversationViewAttachesListener)
            LLog.v("awaitClose() called: ttsRequest: $ttsRequest.")
        }
    }

    override fun askQuestionSimpleFlow(message: String): Flow<Map<String, Any>> =
        // FIXME : Restore me.
        // askQuestionFlow(TtsRequest.create(message))
        askQuestionFlow(TtsRequest.create(
            speech = message,
            isShowOnConversationLayer = true,
            showAnimationOnly = true
        ))

    override fun wakeUpFlow(isOneShot: Boolean) = callbackFlow {
        val asrListener = object : Robot.AsrListener {
            override fun onAsrResult(asrResult: String, sttLanguage: SttLanguage) {
                core.finishConversation()

                val parameterMap = mapOf(
                    "asrResult" to asrResult,
                    "sttLanguage" to sttLanguage
                )

                @OptIn(DelicateCoroutinesApi::class)
                val isClosed = isClosedForSend

                if (isClosed) {
                    core.removeAsrListener(this)
                    LLog.v("Closed callbackFlow try to emit: parameterMap: $parameterMap," +
                            " isOneShot: $isOneShot.")
                    return
                } else if (trySend(parameterMap).isFailure) {
                    LLog.w("trySend() is failed.")
                }

                if (isOneShot) {
                    core.removeAsrListener(this)
                    close()
                }
                LLog.v("parameterMap: $parameterMap.")
            }
        }
        val conversationViewAttachesListener = object : Robot.ConversationViewAttachesListener {
            override fun onConversationAttaches(isAttached: Boolean) {
                @OptIn(DelicateCoroutinesApi::class)
                val isClosed = isClosedForSend

                if (isClosed) {
                    core.removeConversationViewAttachesListener(this)
                    LLog.v("Closed callbackFlow try to emit: isOneShot: $isOneShot.")
                    return
                } else if (!isAttached && isOneShot) {
                    core.removeConversationViewAttachesListener(this)
                    close()
                }
                LLog.v("isAttached: $isAttached.")
            }
        }

        core.addAsrListener(asrListener)
        core.addConversationViewAttachesListener(conversationViewAttachesListener)
        core.wakeup()

        LLog.v("isOneShot: $isOneShot.")
        awaitClose {
            core.removeAsrListener(asrListener)
            core.removeConversationViewAttachesListener(conversationViewAttachesListener)
            LLog.v("awaitClose() called: isOneShot: $isOneShot.")
        }
    }

    override fun registerPrivateSpeechObservers() {
        core.addTtsListener(privateTtsListener)
        core.addAsrListener(privateAsrListener)
        core.addConversationViewAttachesListener(privateConversationViewAttachesListener)
        LLog.v()
    }

    override fun unregisterPrivateSpeechObservers() {
        core.removeTtsListener(privateTtsListener)
        core.removeAsrListener(privateAsrListener)
        core.removeConversationViewAttachesListener(privateConversationViewAttachesListener)
        LLog.v()
    }

    override fun clearAllSpeechListeners(parent: Any?) {
        if (parent == null) {
            ttsListenerMap.clear()
            asrListenerMap.clear()
            conversationViewAttachesListenerMap.clear()
            LLog.v("parent is null. It will be removed all.")
            return
        }

        clearAllTtsListeners(parent)
        clearAllAsrListeners(parent)
        clearAllConversationViewAttachesListeners(parent)
        LLog.v("parent: $parent.")
    }

    override fun addTtsListener(
        parent: Any, listener: Robot.TtsListener
    ) = TemiRobotDelegators.addListener(ttsListenerMap, parent, listener)

    override fun removeTtsListener(
        parent: Any, listener: Robot.TtsListener
    ) = TemiRobotDelegators.removeListener(ttsListenerMap, parent, listener)

    override fun clearAllTtsListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(ttsListenerMap, parent)

    override fun addAsrListener(
        parent: Any, listener: Robot.AsrListener
    ) = TemiRobotDelegators.addListener(asrListenerMap, parent, listener)

    override fun removeAsrListener(
        parent: Any, listener: Robot.AsrListener
    ) = TemiRobotDelegators.removeListener(asrListenerMap, parent, listener)

    override fun clearAllAsrListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(asrListenerMap, parent)

    override fun addConversationViewAttachesListener(
        parent: Any, listener: Robot.ConversationViewAttachesListener,
    ) = TemiRobotDelegators.addListener(conversationViewAttachesListenerMap, parent, listener)

    override fun removeConversationViewAttachesListener(
        parent: Any, listener: Robot.ConversationViewAttachesListener,
    ) = TemiRobotDelegators.removeListener(conversationViewAttachesListenerMap, parent, listener)

    override fun clearAllConversationViewAttachesListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(conversationViewAttachesListenerMap, parent)
}