package kr.bluevisor.robot.libs.core.platform.robot

import android.os.Build
import androidx.annotation.RequiresApi
import com.robotemi.sdk.Robot
import com.robotemi.sdk.listeners.OnMovementStatusChangedListener
import enn.libs.and.llog.LLog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import java.util.WeakHashMap

interface TemiRobotMovement {
    enum class Type(val value: String) {
        SKID_JOY(OnMovementStatusChangedListener.TYPE_SKID_JOY),
        TURN_BY(OnMovementStatusChangedListener.TYPE_TURN_BY);

        companion object {
            fun valueFrom(value: String) =
                TemiRobotDelegators.valueFrom(entries, value, Type::value)
        }
    }

    enum class Status(val value: String) {
        START(OnMovementStatusChangedListener.STATUS_START),
        GOING(OnMovementStatusChangedListener.STATUS_GOING),
        OBSTACLE_DETECTED(OnMovementStatusChangedListener.STATUS_OBSTACLE_DETECTED),
        NODE_INACTIVE(OnMovementStatusChangedListener.STATUS_NODE_INACTIVE),
        CALCULATING(OnMovementStatusChangedListener.STATUS_CALCULATING),
        COMPLETE(OnMovementStatusChangedListener.STATUS_COMPLETE),
        ABORT(OnMovementStatusChangedListener.STATUS_ABORT);

        companion object {
            fun valueFrom(value: String) =
                TemiRobotDelegators.valueFrom(entries, value, Status::value)
        }
    }

    val typeFlow: StateFlow<Type?>
    val statusFlow: StateFlow<Status>

    fun turnBy(degree: Int, speed: Float = 1.0f)
    fun stopMovement()
    fun turnByFlow(degree: Int, speed: Float = 1.0f): Flow<Map<String, Any>>

    fun registerPrivateMovementObservers()
    fun unregisterPrivateMovementObservers()
    fun clearAllMovementListeners(parent: Any? = null)
    fun addOnMovementStatusChangedListener(
        parent: Any, listener: OnMovementStatusChangedListener)
    fun removeOnMovementStatusChangedListener(
        parent: Any, listener: OnMovementStatusChangedListener)
    fun clearAllOnMovementStatusChangedListeners(parent: Any)

    class MovementAbortException : RuntimeException {
        constructor() : super()

        @Suppress("unused")
        constructor(message: String?) : super(message)

        @Suppress("unused")
        constructor(message: String?, cause: Throwable?) : super(message, cause)

        @Suppress("unused")
        constructor(cause: Throwable?) : super(cause)

        @Suppress("unused")
        @RequiresApi(Build.VERSION_CODES.N)
        constructor(
            message: String?,
            cause: Throwable?,
            enableSuppression: Boolean,
            writableStackTrace: Boolean,
        ) : super(message, cause, enableSuppression, writableStackTrace)
    }
}

class TemiRobotMovementDelegator(private val core: Robot) : TemiRobotMovement {
    private val _typeFlow = MutableStateFlow<TemiRobotMovement.Type?>(null)
    override val typeFlow = _typeFlow.asStateFlow()

    private val _statusFlow = MutableStateFlow(TemiRobotMovement.Status.COMPLETE)
    override val statusFlow = _statusFlow.asStateFlow()

    private val privateMovementStatusChangedListener =
        newPrivateMovementStatusChangedListener()
    private val movementStatusChangedListenerMap =
        WeakHashMap<Any, MutableSet<OnMovementStatusChangedListener>>()

    private fun newPrivateMovementStatusChangedListener()
    = object : OnMovementStatusChangedListener {
        override fun onMovementStatusChanged(type: String, status: String) {
            _typeFlow.value = TemiRobotMovement.Type.valueFrom(type)
            _statusFlow.value = TemiRobotMovement.Status.valueFrom(status)

            movementStatusChangedListenerMap.values.forEach { listenerSet ->
                listenerSet.forEach { it.onMovementStatusChanged(type, status) }
            }
            LLog.v("type: $type, status: $status.")
        }
    }

    override fun turnBy(degree: Int, speed: Float) = core.turnBy(degree, speed)

    override fun stopMovement() = core.stopMovement()

    override fun turnByFlow(degree: Int, speed: Float) = callbackFlow {
        val movementStatusChangedListener = object : OnMovementStatusChangedListener {
            override fun onMovementStatusChanged(type: String, status: String) {
                val movementType = TemiRobotMovement.Type.valueFrom(type)
                if (movementType != TemiRobotMovement.Type.TURN_BY) return

                val statusType = TemiRobotMovement.Status.valueFrom(status)

                val parameterMap = mapOf(
                    "movementType" to movementType,
                    "statusType" to statusType
                )

                @OptIn(DelicateCoroutinesApi::class)
                val isClosed = isClosedForSend
                if (isClosed) {
                    core.removeOnMovementStatusChangedListener(this)
                    LLog.v("Closed callbackFlow try to emit: parameterMap: $parameterMap," +
                            " movementType: $movementType, statusType: $statusType.")
                    return
                } else if (trySend(parameterMap).isFailure) {
                    LLog.w("trySend() is failed.")
                }

                when (statusType) {
                    TemiRobotMovement.Status.COMPLETE -> {
                        core.removeOnMovementStatusChangedListener(this)
                        close()
                    }
                    TemiRobotMovement.Status.ABORT -> {
                        core.removeOnMovementStatusChangedListener(this)
                        close(TemiRobotMovement.MovementAbortException())
                    }
                    else -> {}
                }
                LLog.v("parameterMap: $parameterMap.")
            }
        }
        core.addOnMovementStatusChangedListener(movementStatusChangedListener)
        core.turnBy(degree, speed)

        LLog.v("degree: $degree, speed: $speed.")
        awaitClose {
            core.removeOnMovementStatusChangedListener(movementStatusChangedListener)
            LLog.v("awaitClose() called: degree: $degree, speed: $speed.")
        }
    }

    override fun registerPrivateMovementObservers() {
        core.addOnMovementStatusChangedListener(privateMovementStatusChangedListener)
        LLog.v()
    }

    override fun unregisterPrivateMovementObservers() {
        core.removeOnMovementStatusChangedListener(privateMovementStatusChangedListener)
        LLog.v()
    }

    override fun clearAllMovementListeners(parent: Any?) {
        if (parent == null) {
            movementStatusChangedListenerMap.clear()
            LLog.v("parent is null. It will be removed all.")
            return
        }

        clearAllOnMovementStatusChangedListeners(parent)
        LLog.v("parent: $parent.")
    }

    override fun addOnMovementStatusChangedListener(
        parent: Any, listener: OnMovementStatusChangedListener,
    ) = TemiRobotDelegators.addListener(movementStatusChangedListenerMap, parent, listener)

    override fun removeOnMovementStatusChangedListener(
        parent: Any, listener: OnMovementStatusChangedListener,
    ) = TemiRobotDelegators.removeListener(movementStatusChangedListenerMap, parent, listener)

    override fun clearAllOnMovementStatusChangedListeners(parent: Any) =
        TemiRobotDelegators.clearAllListeners(movementStatusChangedListenerMap, parent)
}