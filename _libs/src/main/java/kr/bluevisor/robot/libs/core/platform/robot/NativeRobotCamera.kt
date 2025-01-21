package kr.bluevisor.robot.libs.core.platform.robot

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import enn.libs.and.llog.LLog
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.flow.withIndex
import kr.bluevisor.robot.libs.core.platform.media.camera.NativeCameraController
import kr.bluevisor.robot.libs.presentation.ui.NativeRobotCameraActivity
import kotlin.time.Duration.Companion.seconds

class NativeRobotCamera(private val context: Context) {
    private val _latestPhotoUriStateFlow = MutableStateFlow<Uri?>(null)
    val latestPhotoUriStateFlow = _latestPhotoUriStateFlow.asStateFlow()

    private val cameraController = NativeCameraController(context)

    val initialized = cameraController.initialized

    fun init() = cameraController.init()

    fun bind(lifecycleOwner: LifecycleOwner, previewView: PreviewView? = null) {
        cameraController.bindCamera(
            lifecycleOwner = lifecycleOwner,
            newPreviewUseCase =
            if (previewView != null) {
                { resolutionSelector, screenRotation: Int ->
                    cameraController.newSimplePreviewUseCase(
                        resolutionSelector, screenRotation, previewView
                    )
                }
            } else null,
            newImageCaptureUseCase = cameraController::newSimpleImageCaptureUseCase
        )
        LLog.v()
    }

    fun takePhotoFlow(pictureDirectoryName: String): Flow<Uri?> {
        val resultFlow = cameraController.takePhoto(pictureDirectoryName)
            .catch { cause ->
                LLog.w(cause)
                throw cause
            }
            .onEach { uri ->
                _latestPhotoUriStateFlow.value = uri
                LLog.v("uri: $uri.")
            }

        LLog.v("pictureDirectoryName: $pictureDirectoryName.")
        return resultFlow
    }

    @OptIn(FlowPreview::class)
    fun takePhotoWithPreviewFlow() = callbackFlow {
        context.startActivity(
            Intent(context, NativeRobotCameraActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
        LLog.v()

        latestPhotoUriStateFlow
            .timeout(10.seconds)
            .catch { cause ->
                if (cause is TimeoutCancellationException) {
                    emit(null)
                    LLog.w("Timeout to take photo. camera may emit null.")
                } else {
                    LLog.w(cause)
                    throw cause
                }
            }
            .withIndex()
            .collect { indexedUri ->
                if (indexedUri.index == 0) return@collect
                if (trySend(indexedUri.value).isFailure) {
                    LLog.w("trySend is failed.")
                }
                close()
                LLog.v("indexedUri: $indexedUri.")
            }
        awaitClose { LLog.v() }
    }

    fun unbindAll() = cameraController.unbindAll()
}