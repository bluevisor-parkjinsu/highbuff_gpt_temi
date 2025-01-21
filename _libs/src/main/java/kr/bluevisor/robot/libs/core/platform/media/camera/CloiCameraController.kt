//package kr.bluevisor.robot.libs.core.platform.media.camera
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.os.Build
//import android.os.Environment
//import android.os.Handler
//import android.view.PixelCopy
//import android.view.SurfaceView
//import androidx.annotation.RequiresApi
//import com.arthenica.ffmpegkit.FFmpegKit
//import com.arthenica.ffmpegkit.FFmpegSession
//import com.arthenica.ffmpegkit.ReturnCode
//import com.lge.thirdpartylib.ThirdPartyLib
//import enn.libs.and.llog.LLog
//import java.io.File
//
//typealias CloiCameraManager = com.lge.rtsp.CameraManager
//typealias CloiVideoCameraHelper = com.lge.rtsp.CameraManager.VideoHelper
//
//class CloiCameraController(
//    private val context: Context,
//    private val surfaceView: SurfaceView? = null
//) {
//    fun init() {
//        CloiCameraManager.init(context)
//        CloiVideoCameraHelper.addView(surfaceView, CAMERA_WIDTH, CAMERA_HEIGHT, false);
//        CloiCameraManager.setVideoListener { data, _ ->
//            CloiVideoCameraHelper.makeSpsPps(data)
//        }
//        CloiCameraManager.setMediaCodecListener { data -> }
//        LLog.v()
//    }
//
//    fun start() {
//        var rtspUrl: String? = null
//        try {
//            rtspUrl = ThirdPartyLib.requestPreviewUrl(context).also {
//                CloiCameraManager.cameraPlay(it)
//            }
//        } catch (e: Exception) {
//            LLog.w(e)
//        }
//        LLog.v("rtspUrl: $rtspUrl.")
//    }
//
//    fun stop() {
//        try {
//            CloiCameraManager.cameraStop()
//            CloiCameraManager.clear()
//        } catch (e: UninitializedPropertyAccessException) {
//            LLog.w(e)
//        }
//        LLog.v()
//    }
//
//    fun snapshot(): File {
//        val fileName = "snapshot_${System.currentTimeMillis()}.jpg"
//        ThirdPartyLib.requestSnapshot(context, fileName)
//
//        val snapshotFile = buildString {
//            append(Environment.getExternalStorageDirectory())
//            append(File.separator)
//            append("data")
//            append(File.separator)
//            append(fileName)
//        }.let { File(it) }
//
//        LLog.v("snapshotFile.path: ${snapshotFile.canonicalPath}")
//        return snapshotFile
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    fun snapshotImmediately(
//        callback: (Bitmap) -> Unit,
//        callbackHandler: Handler,
//    ) {
//        val surfaceView = surfaceView ?: run {
//            LLog.w("surfaceView is null. This method will not be called correctly.")
//            return
//        }
//
//        val bitmap = Bitmap.createBitmap(CAMERA_WIDTH, CAMERA_HEIGHT, Bitmap.Config.ARGB_8888)
//        PixelCopy.request(
//            surfaceView,
//            bitmap,
//            PixelCopy.OnPixelCopyFinishedListener {
//                callback(bitmap);
//                LLog.v("copyResult: $it.")
//            },
//            callbackHandler
//        )
//        LLog.v()
//    }
//
//    class VideoStreamer {
//        private var ffmpegSession: FFmpegSession? = null
//
//        fun transportRtspStream(rtspServerUrl: String, context: Context) {
//            val commandToken = buildString {
//                append("-re -rtsp_transport tcp")
//                append(" -i ${getRtspUrl(context)}")
//                append(" -c copy -f rtsp -rtsp_transport tcp $rtspServerUrl")
//            }
//
//            ffmpegSession?.cancel()
//            ffmpegSession = FFmpegKit.executeAsync(
//                commandToken,
//                { session ->
//                    when {
//                        ReturnCode.isSuccess(session.returnCode) -> {
//                            LLog.v("Succeed.")
//                        }
//
//                        ReturnCode.isCancel(session.returnCode) -> {
//                            LLog.v("Canceled.")
//                        }
//
//                        else -> {
//                            LLog.w()
//                        }
//                    }
//                },
//                { log -> },
//                { statistics -> }
//            )
//            LLog.v("rtspServerUrl: $rtspServerUrl.")
//        }
//
//        fun cancelTasking() {
//            ffmpegSession?.cancel()
//            LLog.v()
//        }
//    }
//
//    companion object {
//        private const val CAMERA_WIDTH = 1920
//        private const val CAMERA_HEIGHT = 1080
//
//        fun getRtspUrl(context: Context) = ThirdPartyLib.requestPreviewUrl(context)
//    }
//}