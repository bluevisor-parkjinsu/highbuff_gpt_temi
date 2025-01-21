package kr.bluevisor.robot.libs.data

class GptApiContract {
    companion object {
        const val BASE_URL = "https://api.openai.com/"
        const val URL_PATH__V1_CHAT_COMPLETIONS = "v1/chat/completions"
        const val URL_PATH__V1_AUDIO_SPEECH = "v1/audio/speech"
        const val URL_PATH__V1_AUDIO_TRANSCRIPTIONS = "v1/audio/transcriptions"
        const val URL_PATH__V1_AUDIO_TRANSLATIONS = "v1/audio/translations"
    }
}