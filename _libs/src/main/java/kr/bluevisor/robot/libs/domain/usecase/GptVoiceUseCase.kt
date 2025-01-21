package kr.bluevisor.robot.libs.domain.usecase

import kr.bluevisor.robot.libs.data.repository.GptVoiceRepository
import kr.bluevisor.robot.libs.domain.entity.GptAudioSpeech
import kr.bluevisor.robot.libs.domain.entity.GptAudioToEnglishTextTranslation
import kr.bluevisor.robot.libs.domain.entity.GptAudioTranscription
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GptVoiceUseCase @Inject constructor(private val repository: GptVoiceRepository) {
    fun requestToCreateSpeech(speech: GptAudioSpeech) =
        repository.requestToCreateSpeech(speech)

    fun requestAudioTranscription(audioOnlyTranscription: GptAudioTranscription) =
        repository.requestAudioTranscription(audioOnlyTranscription)

    fun requestAudioToEnglishTextTranslation(
        audioOnlyTranslation: GptAudioToEnglishTextTranslation
    ) = repository.requestAudioToEnglishTextTranslations(audioOnlyTranslation)
}