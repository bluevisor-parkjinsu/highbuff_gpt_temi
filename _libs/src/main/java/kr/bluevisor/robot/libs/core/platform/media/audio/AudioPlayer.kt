package kr.bluevisor.robot.libs.core.platform.media.audio

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import enn.libs.and.llog.LLog
import java.io.File
import javax.inject.Inject

interface AudioPlayable {
    fun startAudioPlaying(audioFile: File)
    fun startAudioReplaying(): File?
    fun stopAudioPlaying(): File?
    fun release()
}

@ActivityScoped
class AudioPlayer @Inject constructor(
    @ActivityContext context: Context
) : AudioPlayable, DefaultLifecycleObserver {
    private val mediaPlayer: MediaPlayer
    var latestAudioFile: File? = null

    val existLatestAudioFile
        get() = latestAudioFile?.exists() ?: false

    init {
        mediaPlayer =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
                MediaPlayer(context)
            else
                MediaPlayer()
    }

    override fun startAudioPlaying(audioFile: File) {
        stopAudioPlaying()
        with(mediaPlayer) {
            reset()
            setOnPreparedListener {
                it.start()
                LLog.v("onPrepared.")
            }

            setDataSource(audioFile.canonicalPath)
            prepareAsync()
        }

        latestAudioFile = audioFile
        LLog.v("audioFile: $audioFile.")
    }

    override fun startAudioReplaying(): File? {
        val latestAudioFile = latestAudioFile ?: return null
        if (!existLatestAudioFile) return null

        startAudioPlaying(latestAudioFile)
        LLog.v("latestAudioFile: $latestAudioFile.")
        return latestAudioFile
    }

    override fun stopAudioPlaying(): File? {
        mediaPlayer
            .runCatching { if(isPlaying) stop() }
            .onFailure { LLog.w(it) }
        LLog.v("latestAudioFile: $latestAudioFile.")
        return latestAudioFile
    }

    override fun release() {
        stopAudioPlaying()
        mediaPlayer.release()
        LLog.v()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopAudioPlaying()
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

    fun setInnerMediaPlayerOnCompletionListener(listener: MediaPlayer.OnCompletionListener?) {
        mediaPlayer.setOnCompletionListener(listener)
        LLog.v("listener: $listener.")
    }

    fun setInnerMediaPlayerOnErrorListener(listener: MediaPlayer.OnErrorListener?) {
        mediaPlayer.setOnErrorListener(listener)
        LLog.v("listener: $listener.")
    }
}