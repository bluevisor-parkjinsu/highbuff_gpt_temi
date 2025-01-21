package kr.bluevisor.robot.libs.core.platform.media.audio

import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.scopes.ActivityScoped
import enn.libs.and.llog.LLog
import java.io.File
import javax.inject.Inject

@ActivityScoped
class AudioMediator @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val audioRecorder: AudioRecorder
) : AudioPlayable, AudioRecordable, DefaultLifecycleObserver {
    override val isAudioRecording
        get() = audioRecorder.isAudioRecording

    val latestPlayingAudioFile
        get() = audioPlayer.latestAudioFile

    val latestRecordingAudioFile
        get() = audioRecorder.latestAudioFile

    override fun startAudioPlaying(audioFile: File) {
        audioRecorder.stopAudioRecording()
        audioPlayer.startAudioPlaying(audioFile)
        LLog.v("audioFile: $audioFile.")
    }

    override fun startAudioReplaying(): File? {
        audioRecorder.stopAudioRecording()
        val resultFile = audioPlayer.startAudioReplaying()
        LLog.v("resultFile: $resultFile.")
        return resultFile
    }

    override fun stopAudioPlaying(): File? {
        val resultFile = audioRecorder.stopAudioRecording()
        LLog.v("resultFile: $resultFile.")
        return resultFile
    }

    override fun startAudioRecording(fileName: String, directory: File): File {
        audioPlayer.stopAudioPlaying()
        val resultFile = audioRecorder.startAudioRecording(fileName, directory)
        LLog.v("fileName: $fileName, directory: $directory, resultFile: $resultFile.")
        return resultFile
    }

    override fun stopAudioRecording(): File? {
        val resultFile = audioRecorder.stopAudioRecording()
        LLog.v("resultFile: $resultFile.")
        return resultFile
    }

    override fun release() {
        audioPlayer.release()
        audioRecorder.release()
        LLog.v()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopMediaRunning()
        LLog.v()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        release()
        LLog.v()
    }

    fun stopMediaRunning() {
        audioPlayer.stopAudioPlaying()
        audioRecorder.stopAudioRecording()
        LLog.v()
    }

    fun deleteLatestAudioFiles() {
        audioPlayer.deleteLatestAudioFile()
        audioRecorder.deleteLatestAudioFile()
        LLog.v()
    }

    fun setInnerMediaPlayerOnCompletionListener(listener: MediaPlayer.OnCompletionListener?) {
        audioPlayer.setInnerMediaPlayerOnCompletionListener(listener)
        LLog.v("listener: $listener.")
    }

    fun setInnerMediaPlayerOnErrorListener(listener: MediaPlayer.OnErrorListener?) {
        audioPlayer.setInnerMediaPlayerOnErrorListener(listener)
        LLog.v("listener: $listener.")
    }

    fun setInnerMediaRecorderOnInfoListener(listener: MediaRecorder.OnInfoListener?) {
        audioRecorder.setInnerMediaRecorderOnInfoListener(listener)
        LLog.v("listener: $listener.")
    }

    fun setInnerMediaRecorderOnErrorListener(listener: MediaRecorder.OnErrorListener?) {
        audioRecorder.setInnerMediaRecorderOnErrorListener(listener)
        LLog.v("listener: $listener.")
    }
}