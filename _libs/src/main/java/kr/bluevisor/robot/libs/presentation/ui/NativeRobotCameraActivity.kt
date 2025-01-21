package kr.bluevisor.robot.libs.presentation.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.gun0912.tedpermission.coroutine.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.bluevisor.robot.libs.core.platform.robot.NativeRobot
import kr.bluevisor.robot.libs.presentation.theme.MainTheme
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@Composable
private fun NativeRobotNavigation(
    previewViewAssigner: (PreviewView) -> Unit,
    startDestination: String = NativeRobotCameraActivity.SCREEN__ALIAS__DEFAULT
) {
    val navGraphName = "navGraph"
    val navController = rememberNavController()

    NavHost(navController, startDestination = navGraphName) {
        navigation(route = navGraphName, startDestination = startDestination) {
            composable(NativeRobotCameraActivity.SCREEN__CAMERA) {
                NativeRobotCameraScreen(navController, previewViewAssigner)
            }
        }
    }
}

@AndroidEntryPoint
class NativeRobotCameraActivity : ComponentActivity() {
    @Inject lateinit var nativeRobot: NativeRobot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cameraInitializingJob = lifecycleScope.launch(Dispatchers.IO) {
            if (nativeRobot.camera.initialized) return@launch
            nativeRobot.camera.init()
        }

        val startDestination = intent.getStringExtra(KEY_INTENT_EXTRA__START_DESTINATION_SCREEN)
            ?: SCREEN__ALIAS__DEFAULT
        setContent {
            MainTheme {
                NativeRobotNavigation(
                    previewViewAssigner = { previewView ->
                        lifecycleScope.launch(Dispatchers.IO) {
                            cameraInitializingJob.join()
                            previewView.post {
                                nativeRobot.camera.bind(
                                    this@NativeRobotCameraActivity, previewView
                                )
                            }

                            delay(1.seconds)
                            nativeRobot.camera.takePhotoFlow(nativeRobot.name)
                                .launchIn(lifecycleScope)
                                .join()
                            withContext(Dispatchers.Main) {
                                finish()
                            }
                        }
                    },
                    startDestination = startDestination
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
        LLog.v()
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeRobot.camera.unbindAll()
        LLog.v()
    }

    private fun checkPermissions() {
        lifecycleScope.launch {
            val result = TedPermission.create()
                .setDeniedMessage(buildString {
                    append("If you reject permission,you can not use this service")
                    append("\n\nPlease turn on permissions at [Setting] > [Permission]")
                })
                .setPermissions(Manifest.permission.CAMERA)
                .check()
            LLog.v("result: $result.")
        }
        LLog.v()
    }

    companion object {
        const val KEY_INTENT_EXTRA__START_DESTINATION_SCREEN = "startDestination"
        const val SCREEN__CAMERA = "camera"
        const val SCREEN__ALIAS__DEFAULT = SCREEN__CAMERA
    }
}