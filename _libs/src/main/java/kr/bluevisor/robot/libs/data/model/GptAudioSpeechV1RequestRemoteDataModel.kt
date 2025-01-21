package kr.bluevisor.robot.libs.data.model

data class GptAudioSpeechV1RequestRemoteDataModel(
    val model: String = FIELD_VALUE__MODEL__TTS_1,
    val input: String,
    val voice: String = FIELD_VALUE__VOICE__ALLOY,
    val response_format: String? = null,
    val speed: Double? = null
) {
    companion object {
        const val FIELD_VALUE__MODEL__TTS_1 = "tts-1"
        const val FIELD_VALUE__MODEL__TTS_1_HD = "tts-1-hd"
        const val FIELD_VALUE__VOICE__ALLOY = "alloy"
        const val FIELD_VALUE__VOICE__ECHO = "echo"
        const val FIELD_VALUE__VOICE__FABLE = "fable"
        const val FIELD_VALUE__VOICE__ONYX = "onyx"
        const val FIELD_VALUE__VOICE__NOVA = "nova"
        const val FIELD_VALUE__VOICE__SHIMMER = "shimmer"
        const val FIELD_VALUE__RESPONSE_FORMAT__MP3 = "mp3"
        const val FIELD_VALUE__RESPONSE_FORMAT__OPUS = "opus"
        const val FIELD_VALUE__RESPONSE_FORMAT__AAC = "aac"
        const val FIELD_VALUE__RESPONSE_FORMAT__FLAC = "flac"
        const val FIELD_VALUE__RESPONSE_FORMAT__WAV = "wav"
        const val FIELD_VALUE__RESPONSE_FORMAT__PCM = "pcm"
    }
}