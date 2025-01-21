package kr.bluevisor.robot.libs.core.platform.robot

import com.robotemi.sdk.Robot
import enn.libs.and.llog.LLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemiRobot @Inject constructor(private val core: Robot) :
    TemiRobotSpeech by TemiRobotSpeechDelegator(core),
    TemiRobotNavigation by TemiRobotNavigationDelegator(core),
    TemiRobotFollow by TemiRobotFollowDelegator(core),
    TemiRobotMovement by TemiRobotMovementDelegator(core),
    TemiRobotDetection by TemiRobotDetectionDelegator(core),
    TemiRobotTelepresence by TemiRobotTelepresenceDelegator(core),
    TemiRobotPermission by TemiRobotPermissionDelegator(core) {

    init {
        registerPrivateListeners()
    }

    fun registerPrivateListeners() {
        registerPrivateSpeechObservers()
        registerPrivateNavigationObservers()
        registerPrivateFollowObservers()
        registerPrivateMovementObservers()
        registerPrivateDetectionObservers()
        registerPrivateTelepresenceObservers()
        registerPrivatePermissionObservers()
        LLog.v()
    }

    fun unregisterPrivateListeners() {
        unregisterPrivateSpeechObservers()
        unregisterPrivateNavigationObservers()
        unregisterPrivateFollowObservers()
        unregisterPrivateMovementObservers()
        unregisterPrivateDetectionObservers()
        unregisterPrivateTelepresenceObservers()
        unregisterPrivatePermissionObservers()
        LLog.v()
    }

    fun clearAllListeners(parent: Any? = null) {
        clearAllSpeechListeners(parent)
        clearAllNavigationListeners(parent)
        clearAllFollowListeners(parent)
        clearAllMovementListeners(parent)
        clearAllDetectionListeners(parent)
        clearAllTelepresenceListeners(parent)
        clearAllPermissionListeners(parent)
        LLog.v("parent: $parent.")
    }
}