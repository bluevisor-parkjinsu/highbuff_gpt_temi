package kr.bluevisor.robot.libs.core.platform.media.camera

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.Display
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraState
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.window.layout.WindowMetricsCalculator
import enn.libs.and.llog.LLog
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class NativeCameraController(private val context: Context) {
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraSelector: CameraSelector
    private var camera: Camera? = null
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null

    private val displayManager =
        context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    private val display: Display
        get() = displayManager.getDisplay(Display.DEFAULT_DISPLAY)

    private val displayManagerListener = newDisplayManagerListener(display)
    private val cameraStateObserver = newCameraStateObserver()

    val initialized: Boolean
        get() = ::cameraProvider.isInitialized
    val selectedLensFacing: Int?
        get() = camera?.cameraInfo?.lensFacing
    val hasFrontCamera: Boolean
        get() = cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
    val hasBackCamera: Boolean
        get() = cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)

    fun init() {
        cameraProvider = ProcessCameraProvider.getInstance(context).get()
        cameraSelector = CameraSelector.Builder()
            .requireLensFacing(calcInitialLensFacing())
            .build()
    }

    fun bindCamera(
        lifecycleOwner: LifecycleOwner,
        newPreviewUseCase: ((ResolutionSelector, screenRotation: Int) -> Preview)? = null,
        newImageCaptureUseCase: ((ResolutionSelector, screenRotation: Int) -> ImageCapture)? = null,
    ) {
        val screenRect = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(context)
            .bounds
        val screenRotation = display.rotation
        val resolutionSelector = newResolutionSelector(screenRect)

        preview = newPreviewUseCase?.invoke(resolutionSelector, screenRotation)
        imageCapture = newImageCaptureUseCase?.invoke(resolutionSelector, screenRotation)

        unbindAll()

        displayManager.registerDisplayListener(displayManagerListener, null)
        camera = cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            *(listOfNotNull(preview, imageCapture).toTypedArray())
        ).apply {
            cameraInfo.cameraState.observe(lifecycleOwner, cameraStateObserver)
        }

        LLog.v(
            "screenRect: $screenRect, screenRotation: $screenRotation," +
                    " preview.isNull: ${preview == null}," +
                    " imageCapture.isNull: ${imageCapture == null}."
        )
    }

    fun takePhoto(pictureDirectoryName: String) = callbackFlow {
        val name = SimpleDateFormat(FILENAME_DATETIME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/$pictureDirectoryName"
                )
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture?.takePicture(
            outputOptions,
            { runnable -> runnable.run() },
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri
                    if (trySend(savedUri).isFailure) {
                        LLog.w("trySend() is failed.")
                    }

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        context.sendBroadcast(
                            Intent(
                                @Suppress("DEPRECATION")
                                android.hardware.Camera.ACTION_NEW_PICTURE,
                                savedUri
                            )
                        )
                    }

                    close()
                    LLog.v("savedUri: $savedUri.")
                }

                override fun onError(exception: ImageCaptureException) {
                    close(exception)
                    LLog.w(exception)
                }
            }
        )

        LLog.v("pictureDirectoryName: $pictureDirectoryName.")
        awaitClose { LLog.v("awaitClose() called: directoryName: $pictureDirectoryName.") }
    }

    private fun calcInitialLensFacing(): Int {
        val lensFacing = when {
            hasBackCamera -> CameraSelector.LENS_FACING_BACK
            hasFrontCamera -> CameraSelector.LENS_FACING_FRONT
            else -> CameraSelector.LENS_FACING_UNKNOWN
        }

        LLog.v("lensFacing: $lensFacing.")
        return lensFacing
    }

    private fun newResolutionSelector(screenRect: Rect): ResolutionSelector {
        val screenWidth = screenRect.width()
        val screenHeight = screenRect.height()
        val previewRatio =
            max(screenWidth, screenHeight) / min(screenWidth, screenHeight).toDouble()
        val screenAspectRatioStrategy =
            if (abs(previewRatio - RATIO_4_3) <= abs(previewRatio - RATIO_16_9)) {
                AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY
            } else {
                AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY
            }

        LLog.v(
            "screenRect: $screenRect, screenWidth: $screenWidth, screenHeight: $screenHeight," +
                    " screenAspectRatioStrategy: $screenAspectRatioStrategy."
        )
        return ResolutionSelector.Builder()
            .setAspectRatioStrategy(screenAspectRatioStrategy)
            .build()
    }

    private fun newCameraStateObserver() = Observer<CameraState> {
        LLog.v("cameraState: $it, error: ${it.error}.")
    }

    private fun newDisplayManagerListener(display: Display) = object :
        DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) {
            if (displayId == display.displayId) {
                imageCapture?.targetRotation = display.rotation
                LLog.v("rotation: ${display.rotation}.")
            }
            LLog.v("displayId: $displayId.")
        }
    }

    fun newSimplePreviewUseCase(
        resolutionSelector: ResolutionSelector,
        screenRotation: Int,
        previewView: PreviewView
    ): Preview {
        return Preview.Builder()
            .setResolutionSelector(resolutionSelector)
            .setTargetRotation(screenRotation)
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }
    }

    fun newSimpleImageCaptureUseCase(
        resolutionSelector: ResolutionSelector,
        screenRotation: Int
    ): ImageCapture {
        return ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setResolutionSelector(resolutionSelector)
            .setTargetRotation(screenRotation)
            .build()
    }

    private fun unbindCameraStateCallback() =
        camera?.cameraInfo?.cameraState?.removeObserver(cameraStateObserver)

    private fun unbindDisplayManagerCallback() =
        displayManager.unregisterDisplayListener(displayManagerListener)

    fun unbindAll() {
        unbindCameraStateCallback()
        unbindDisplayManagerCallback()
        cameraProvider.unbindAll()
        LLog.v()
    }

    companion object {
        private const val FILENAME_DATETIME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val RATIO_4_3 = 4.0 / 3.0
        private const val RATIO_16_9 = 16.0 / 9.0
    }
}