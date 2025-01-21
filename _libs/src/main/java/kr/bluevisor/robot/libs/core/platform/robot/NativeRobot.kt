package kr.bluevisor.robot.libs.core.platform.robot

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeRobot @Inject constructor(@ApplicationContext context: Context) {
    val name = "Bluevisor Native Robot"
    val camera by lazy { NativeRobotCamera(context) }
}