package kr.bluevisor.robot.libs.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import kr.bluevisor.robot.libs.core.platform.media.audio.AudioPlayable
import kr.bluevisor.robot.libs.core.platform.media.audio.AudioPlayer
import kr.bluevisor.robot.libs.core.platform.media.audio.AudioRecordable
import kr.bluevisor.robot.libs.core.platform.media.audio.AudioRecorder

@Module
@InstallIn(ActivityComponent::class)
interface ActivityBindingModule {
    @Binds
    @ActivityScoped
    fun bindVoiceAudioPlayable(player: AudioPlayer): AudioPlayable

    @Binds
    @ActivityScoped
    fun bindVoiceAudioRecordable(recorder: AudioRecorder): AudioRecordable
}