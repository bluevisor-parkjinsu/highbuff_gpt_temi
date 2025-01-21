package kr.bluevisor.robot.libs.domain.entity

import kr.bluevisor.robot.libs.data.model.GptAudioSpeechV1RequestRemoteDataModel
import enn.libs.and.llog.LLog
import java.io.File

data class GptAudioSpeech(
    val model: ModelType = MODEL_TYPE__DEFAULT,
    val input: String,
    val voice: VoiceType = VOICE_TYPE__DEFAULT,
    val audioFile: File
) {
    init {
        val validAudioFileFormat = audioFile.extension.equals(
            ResponseFormatType.MP3.name, ignoreCase = true)
        if (!validAudioFileFormat) {
            LLog.w("audioFile has invalid file extension. Use .mp3 file extension." +
                    " audioFile: $audioFile.")
        }
    }

    enum class ModelType(val remoteDataFieldValue: String) {
        TTS_1
            (GptAudioSpeechV1RequestRemoteDataModel.FIELD_VALUE__MODEL__TTS_1),
        TTS_1_HD
            (GptAudioSpeechV1RequestRemoteDataModel.FIELD_VALUE__MODEL__TTS_1_HD),
    }

    enum class VoiceType() {
        ALLOY, ECHO, FABLE, ONYX, NOVA, SHIMMER
    }

    enum class ResponseFormatType {
        MP3, OPUS, AAC, FLAC, WAV, PCM
    }

    val existAudioFile
        get() = audioFile.exists()

    companion object {
        val MODEL_TYPE__DEFAULT = ModelType.TTS_1
        val VOICE_TYPE__DEFAULT = VoiceType.ALLOY
    }
}