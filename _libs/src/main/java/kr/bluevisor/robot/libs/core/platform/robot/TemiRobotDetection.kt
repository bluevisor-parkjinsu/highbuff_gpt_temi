package kr.bluevisor.robot.libs.core.platform.robot

import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnDetectionDataChangedListener
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener
import com.robotemi.sdk.model.DetectionData
import enn.libs.and.llog.LLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.WeakHashMap

interface TemiRobotDetection {
    enum class State(val value: Int) {
        DETECTED(OnDetectionStateChangedListener.DETECTED),
        LOST(OnDetectionStateChangedListener.LOST),
        IDLE(OnDetectionStateChangedListener.IDLE);

        companion object {
            fun valueFrom(value: Int) = TemiRobotDelegators.valueFrom(entries, value, State::value)
        }
    }

    val detectionStateFlow: StateFlow<State>
    val detectionDataFlow: StateFlow<DetectionData>
    var detectionModeOn: Boolean

    fun setDetectionModeOn(on: Boolean, distance: Float)

    fun registerPrivateDetectionObservers()
    fun unregisterPrivateDetectionObservers()
    fun clearAllDetectionListeners(parent: Any? = null)
    fun addOnDetectionStateChangedListener(parent: Any, listener: OnDetectionStateChangedListener)
    fun removeOnDetectionStateChangedListener(parent: Any, listener: OnDetectionStateChangedListener)
    fun clearAllOnDetectionStateChangedListeners(parent: Any)
    fun addOnDetectionDataChangedListener(parent: Any, listener: OnDetectionDataChangedListener)
    fun removeOnDetectionDataChangedListener(parent: Any, listener: OnDetectionDataChangedListener)
    fun clearAllOnDetectionDataChangedListeners(parent: Any)
}

class TemiRobotDetectionDelegator(private val core: Robot) : TemiRobotDetection {
    private val _detectionStateFlow = MutableStateFlow(TemiRobotDetection.State.IDLE)
    override val detectionStateFlow = _detectionStateFlow.asStateFlow()

    private val _detectionDataFlow = MutableStateFlow(
        DetectionData(angle = 0.0, distance = 0.0, isDetected = false))
    override val detectionDataFlow = _detectionDataFlow.asStateFlow()

    override var detectionModeOn
        get() = core.detectionModeOn
        set(value) { core.detectionModeOn = value }

    private val privateDetectionStateChangedListener =
        newPrivateDetectionStateChangedListener()
    private val detectionStateChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnDetectionStateChangedListener>>()

    private val privateDetectionDataChangedListener =
        newPrivateDetectionDataChangedListener()
    private val detectionDataChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnDetectionDataChangedListener>>()

    private fun newPrivateDetectionStateChangedListener()
    = object : OnDetectionStateChangedListener {
        override fun onDetectionStateChanged(state: Int) {
            val detectionState = TemiRobotDetection.State.valueFrom(state)
            _detectionStateFlow.value = detectionState

            detectionStateChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onDetectionStateChanged(state) }
            }
            LLog.v("state: $state, detectionState: $detectionState.")
        }
    }

    private fun newPrivateDetectionDataChangedListener()
    = object : OnDetectionDataChangedListener {
        override fun onDetectionDataChanged(detectionData: DetectionData) {
            if (detectionData.isDetected) {
                _detectionStateFlow.value = TemiRobotDetection.State.DETECTED
            }
            _detectionDataFlow.value = detectionData

            detectionDataChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onDetectionDataChanged(detectionData) }
            }
            LLog.v("detectionData: $detectionData.")
        }
    }

    override fun setDetectionModeOn(on: Boolean, distance: Float) =
        core.setDetectionModeOn(on, distance)

    override fun registerPrivateDetectionObservers() {
        core.addOnDetectionStateChangedListener(privateDetectionStateChangedListener)
        core.addOnDetectionDataChangedListener(privateDetectionDataChangedListener)
        LLog.v()
    }

    override fun unregisterPrivateDetectionObservers() {
        core.removeOnDetectionStateChangedListener(privateDetectionStateChangedListener)
        core.removeOnDetectionDataChangedListener(privateDetectionDataChangedListener)
        LLog.v()
    }

    override fun clearAllDetectionListeners(parent: Any?) {
        if (parent == null) {
            detectionStateChangedListenerMap.clear()
            detectionDataChangedListenerMap.clear()
            LLog.v("parent is null. It will be removed all listeners.")
            return
        }

        clearAllOnDetectionStateChangedListeners(parent)
        clearAllOnDetectionDataChangedListeners(parent)
        LLog.v("parent: $parent.")
    }

    override fun addOnDetectionStateChangedListener(
        parent: Any, listener: OnDetectionStateChangedListener
    ) = TemiRobotDelegators.addListener(detectionStateChangedListenerMap, parent, listener)

    override fun removeOnDetectionStateChangedListener(
        parent: Any, listener: OnDetectionStateChangedListener
    ) = TemiRobotDelegators.removeListener(detectionStateChangedListenerMap, parent, listener)

    override fun clearAllOnDetectionStateChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(detectionStateChangedListenerMap, parent)

    override fun addOnDetectionDataChangedListener(
        parent: Any, listener: OnDetectionDataChangedListener
    ) = TemiRobotDelegators.addListener(detectionDataChangedListenerMap, parent, listener)

    override fun removeOnDetectionDataChangedListener(
        parent: Any, listener: OnDetectionDataChangedListener
    ) = TemiRobotDelegators.removeListener(detectionDataChangedListenerMap, parent, listener)

    override fun clearAllOnDetectionDataChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(detectionDataChangedListenerMap, parent)
}