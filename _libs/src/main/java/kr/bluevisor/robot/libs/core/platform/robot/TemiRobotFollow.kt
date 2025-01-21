package kr.bluevisor.robot.libs.core.platform.robot

import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener
import enn.libs.and.llog.LLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.WeakHashMap

interface TemiRobotFollow {
    enum class BeWithMeMode(val value: String) {
        ABORT(OnBeWithMeStatusChangedListener.ABORT),
        CALCULATING(OnBeWithMeStatusChangedListener.CALCULATING),
        SEARCH(OnBeWithMeStatusChangedListener.SEARCH),
        START(OnBeWithMeStatusChangedListener.START),
        TRACK(OnBeWithMeStatusChangedListener.TRACK),
        OBSTACLE_DETECTED(OnBeWithMeStatusChangedListener.OBSTACLE_DETECTED);

        companion object {
            fun valueFrom(value: String) =
                TemiRobotDelegators.valueFrom(entries, value, BeWithMeMode::value)
        }
    }

    val beWithMeModeFlow: StateFlow<BeWithMeMode>
    val constraintBeWithModeOnFlow: StateFlow<Boolean>

    fun beWithMe()
    fun constraintBeWith()

    fun registerPrivateFollowObservers()
    fun unregisterPrivateFollowObservers()
    fun clearAllFollowListeners(parent: Any? = null)
    fun addOnBeWithMeStatusChangedListener(
        parent: Any, listener: OnBeWithMeStatusChangedListener)
    fun removeOnBeWithMeStatusChangedListener(
        parent: Any, listener: OnBeWithMeStatusChangedListener)
    fun clearAllOnBeWithMeStatusChangedListeners(parent: Any)
    fun addOnConstraintBeWithStatusChangedListener(
        parent: Any, listener: OnConstraintBeWithStatusChangedListener)
    fun removeOnConstraintBeWithStatusChangedListener(
        parent: Any, listener: OnConstraintBeWithStatusChangedListener)
    fun clearAllOnConstraintBeWithStatusChangedListeners(parent: Any)
}

class TemiRobotFollowDelegator(private val core: Robot) : TemiRobotFollow {
    private val _beWithMeModeFlow = MutableStateFlow(TemiRobotFollow.BeWithMeMode.ABORT)
    override val beWithMeModeFlow = _beWithMeModeFlow.asStateFlow()

    private val _constraintBeWithModeOnFlow = MutableStateFlow(false)
    override val constraintBeWithModeOnFlow = _constraintBeWithModeOnFlow.asStateFlow()

    private val privateBeWithMeStatusChangedListener =
        newPrivateBeWithMeStatusChangedListener()
    private val beWithMeStatusChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnBeWithMeStatusChangedListener>>()

    private val privateConstraintBeWithStatusChangedListener =
        newPrivateConstraintBeWithStatusChangedListener()
    private val constraintBeWithStatusChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnConstraintBeWithStatusChangedListener>>()

    private fun newPrivateBeWithMeStatusChangedListener()
    = object : OnBeWithMeStatusChangedListener {
        override fun onBeWithMeStatusChanged(status: String) {
            _beWithMeModeFlow.value = TemiRobotFollow.BeWithMeMode.valueFrom(status)
            beWithMeStatusChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onBeWithMeStatusChanged(status) }
            }
            LLog.v("status: $status.")
        }
    }

    private fun newPrivateConstraintBeWithStatusChangedListener()
    = object : OnConstraintBeWithStatusChangedListener {
        override fun onConstraintBeWithStatusChanged(isConstraint: Boolean) {
            _constraintBeWithModeOnFlow.value = isConstraint
            constraintBeWithStatusChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onConstraintBeWithStatusChanged(isConstraint) }
            }
            LLog.v("isConstraint: $isConstraint.")
        }
    }

    override fun beWithMe() = core.beWithMe()

    override fun constraintBeWith() = core.constraintBeWith()

    override fun registerPrivateFollowObservers() {
        core.addOnBeWithMeStatusChangedListener(privateBeWithMeStatusChangedListener)
        core.addOnConstraintBeWithStatusChangedListener(
            privateConstraintBeWithStatusChangedListener)
        LLog.v()
    }

    override fun unregisterPrivateFollowObservers() {
        core.removeOnBeWithMeStatusChangedListener(privateBeWithMeStatusChangedListener)
        core.removeOnConstraintBeWithStatusChangedListener(
            privateConstraintBeWithStatusChangedListener)
        LLog.v()
    }

    override fun clearAllFollowListeners(parent: Any?) {
        if (parent == null) {
            beWithMeStatusChangedListenerMap.clear()
            constraintBeWithStatusChangedListenerMap.clear()
            LLog.v("parent is null. It will be removed all.")
            return
        }

        clearAllOnBeWithMeStatusChangedListeners(parent)
        clearAllOnConstraintBeWithStatusChangedListeners(parent)
        LLog.v("parent: $parent.")
    }

    override fun addOnBeWithMeStatusChangedListener(
        parent: Any, listener: OnBeWithMeStatusChangedListener,
    ) = TemiRobotDelegators.addListener(beWithMeStatusChangedListenerMap, parent, listener)

    override fun removeOnBeWithMeStatusChangedListener(
        parent: Any, listener: OnBeWithMeStatusChangedListener,
    ) = TemiRobotDelegators.removeListener(beWithMeStatusChangedListenerMap, parent, listener)

    override fun clearAllOnBeWithMeStatusChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(beWithMeStatusChangedListenerMap, parent)

    override fun addOnConstraintBeWithStatusChangedListener(
        parent: Any, listener: OnConstraintBeWithStatusChangedListener,
    ) = TemiRobotDelegators.addListener(constraintBeWithStatusChangedListenerMap, parent, listener)

    override fun removeOnConstraintBeWithStatusChangedListener(
        parent: Any, listener: OnConstraintBeWithStatusChangedListener,
    ) = TemiRobotDelegators.removeListener(constraintBeWithStatusChangedListenerMap, parent, listener)

    override fun clearAllOnConstraintBeWithStatusChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(constraintBeWithStatusChangedListenerMap, parent)
}