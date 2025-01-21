package kr.bluevisor.robot.libs.core.platform.robot

import androidx.compose.foundation.interaction.DragInteraction
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnTelepresenceEventChangedListener
import com.robotemi.sdk.model.CallEventModel
import com.robotemi.sdk.telepresence.Participant
import enn.libs.and.llog.LLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.WeakHashMap

interface TemiRobotTelepresence {
    enum class Type(val value: Int) {
        INCOMING(CallEventModel.TYPE_INCOMING),
        OUTGOING(CallEventModel.TYPE_OUTGOING);

        companion object {
            fun valueFrom(value: Int) = TemiRobotDelegators.valueFrom(entries, value, Type::value)
        }
    }

    enum class State(val value: Int) {
        STARTED(CallEventModel.STATE_STARTED),
        ENDED(CallEventModel.STATE_ENDED);

        companion object {
            fun valueFrom(value: Int) = TemiRobotDelegators.valueFrom(entries, value, State::value)
        }
    }

    val telepresenceStateFlow: StateFlow<State>

    fun startMeeting(
        participants: List<Participant>,
        firstParticipantJoinedAsHost: Boolean,
        blockRobotInteraction: Boolean = false
    ): String
    fun stopTelepresence(): Int

    fun registerPrivateTelepresenceObservers()
    fun unregisterPrivateTelepresenceObservers()
    fun clearAllTelepresenceListeners(parent: Any? = null)
    fun addOnTelepresenceEventChangedListener(
        parent: Any, listener: OnTelepresenceEventChangedListener)
    fun removeOnTelepresenceEventChangedListener(
        parent: Any, listener: OnTelepresenceEventChangedListener)
    fun clearAllOnTelepresenceEventChangedListeners(parent: Any)

    companion object {
        const val START_MEETING__RESULT_CODE__OK = "200"
        const val START_MEETING__RESULT_CODE__REQUIRE_MEETINGS_PERMISSION = "403"
    }
}

class TemiRobotTelepresenceDelegator(private val core: Robot) : TemiRobotTelepresence {
    private val _telepresenceStateFlow = MutableStateFlow(TemiRobotTelepresence.State.ENDED)
    override val telepresenceStateFlow = _telepresenceStateFlow.asStateFlow()

    private val privateTelepresenceEventChangedListener =
        newPrivateTelepresenceEventChangedListener()
    private val telepresenceEventChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnTelepresenceEventChangedListener>>()

    private fun newPrivateTelepresenceEventChangedListener()
    = object : OnTelepresenceEventChangedListener {
        override fun onTelepresenceEventChanged(callEventModel: CallEventModel) {
            val type = TemiRobotTelepresence.Type.valueFrom(callEventModel.type)
            val state = TemiRobotTelepresence.State.valueFrom(callEventModel.state)
            _telepresenceStateFlow.value = state

            telepresenceEventChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onTelepresenceEventChanged(callEventModel) }
            }
            LLog.v("callEventModel: $callEventModel, type: $type, state: $state.")
        }
    }

    override fun startMeeting(
        participants: List<Participant>,
        firstParticipantJoinedAsHost: Boolean,
        blockRobotInteraction: Boolean
    ) = core.startMeeting(participants, firstParticipantJoinedAsHost, blockRobotInteraction)

    override fun stopTelepresence() = core.stopTelepresence()

    override fun registerPrivateTelepresenceObservers() {
        core.addOnTelepresenceEventChangedListener(privateTelepresenceEventChangedListener)
        LLog.v()
    }

    override fun unregisterPrivateTelepresenceObservers() {
        core.removeOnTelepresenceEventChangedListener(privateTelepresenceEventChangedListener)
        LLog.v()
    }

    override fun clearAllTelepresenceListeners(parent: Any?) {
        if (parent == null) {
            telepresenceEventChangedListenerMap.clear()
            LLog.v("parent is null. It will be removed all.")
            return
        }

        clearAllOnTelepresenceEventChangedListeners(parent)
        LLog.v("parent: $parent.")
    }

    override fun addOnTelepresenceEventChangedListener(
        parent: Any, listener: OnTelepresenceEventChangedListener
    ) = TemiRobotDelegators.addListener(telepresenceEventChangedListenerMap, parent, listener)

    override fun removeOnTelepresenceEventChangedListener(
        parent: Any, listener: OnTelepresenceEventChangedListener
    ) = TemiRobotDelegators.removeListener(telepresenceEventChangedListenerMap, parent, listener)

    override fun clearAllOnTelepresenceEventChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(telepresenceEventChangedListenerMap, parent)
}