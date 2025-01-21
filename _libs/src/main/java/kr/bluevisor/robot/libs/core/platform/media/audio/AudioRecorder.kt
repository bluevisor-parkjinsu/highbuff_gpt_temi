package kr.bluevisor.robot.libs.core.platform.media.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import enn.libs.and.llog.LLog
import java.io.File
import javax.inject.Inject

interface AudioRecordable {
    val isAudioRecording: Boolean
    fun startAudioRecording(fileName: String, directory: File): File
    fun stopAudioRecording(): File?
    fun release()
}

@ActivityScoped
class AudioRecorder @Inject constructor(
    @ActivityContext context: Context
) : AudioRecordable, DefaultLifecycleObserver {
    private var _isRecording = false
    private val mediaRecorder: MediaRecorder
    var latestAudioFile: File? = null

    override val isAudioRecording: Boolean
        get() = _isRecording

    val existLatestAudioFile
        get() = latestAudioFile?.exists() ?: false

    init {
        mediaRecorder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                MediaRecorder(context)
            else
                MediaRecorder()
    }

    override fun startAudioRecording(fileName: String, directory: File): File {
        stopAudioRecording()

        val audioFile = File(directory, "$fileName.m4a")
        with(mediaRecorder) {
            reset()
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioFile.canonicalPath)

            prepare()
            start()
        }

        _isRecording = true
        latestAudioFile = audioFile
        LLog.v("fileName: $fileName, directory: $directory, audioFile: $audioFile.")
        return audioFile
    }

    override fun stopAudioRecording(): File? {
        mediaRecorder
            .runCatching { if (_isRecording) stop() }
            .onFailure { LLog.w(it) }
        _isRecording = false
        LLog.v("latestAudioFile: $latestAudioFile.")
        return latestAudioFile
    }

    override fun release() {
        stopAudioRecording()
        mediaRecorder.release()
        LLog.v()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopAudioRecording()
        LLog.v()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        release()
        LLog.v()
    }

    fun deleteLatestAudioFile() {
        latestAudioFile?.delete()
        LLog.v()
    }

    fun setInnerMediaRecorderOnInfoListener(listener: MediaRecorder.OnInfoListener?) {
        mediaRecorder.setOnInfoListener(listener)
        LLog.v("listener: $listener.")
    }

    fun setInnerMediaRecorderOnErrorListener(listener: MediaRecorder.OnErrorListener?) {
        mediaRecorder.setOnErrorListener(listener)
        LLog.v("listener: $listener.")
    }
}