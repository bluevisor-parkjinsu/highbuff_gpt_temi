package kr.bluevisor.robot.libs.data.repository

import kr.bluevisor.robot.libs.data.datasource.GptRemoteDataSource
import kr.bluevisor.robot.libs.data.toGptAudioToEnglishTextTranslationEntity
import kr.bluevisor.robot.libs.data.toGptAudioTranscriptionEntity
import kr.bluevisor.robot.libs.data.toSimpleGptAudioSpeechV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.toSimpleGptAudioToEnglishTextTranslationV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.toSimpleGptAudioTranscriptionV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.domain.entity.GptAudioSpeech
import kr.bluevisor.robot.libs.domain.entity.GptAudioToEnglishTextTranslation
import kr.bluevisor.robot.libs.domain.entity.GptAudioTranscription
import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GptVoiceRepository @Inject constructor(
    private val dataSource: GptRemoteDataSource
) {
    fun requestToCreateSpeech(speech: GptAudioSpeech) = flow {
        if (speech.existAudioFile) {
            LLog.i("speech.audioFile exist. It will be replaced.")
        }

        val request = speech.toSimpleGptAudioSpeechV1RequestRemoteDataModel()
        dataSource
            .requestToCreateSpeech(request)
            .catch {
                LLog.w(it)
                throw it
            }
            .collect { response ->
                val audioFileInputStream = response.audioFileResponseBody.byteStream()
                audioFileInputStream.use {
                    speech.audioFile.delete()
                    speech.audioFile.writeBytes(it.readBytes())
                }
                emit(speech)
                LLog.v("speech: $speech.")
            }
        LLog.v("speech: $speech.")
    }.flowOn(Dispatchers.IO)

    fun requestAudioTranscription(audioOnlyTranscription: GptAudioTranscription) = flow {
        if (!audioOnlyTranscription.existAudioFile) {
            val errorMessage = "audioOnlyTranscription.audioFile does not exist:" +
                    " audioOnlyTranscription: $audioOnlyTranscription."
            LLog.w(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
        if (!audioOnlyTranscription.isAudioOnly) {
            LLog.i("audioOnlyTranscription is not audio only. Its text will be ignored." +
                    " audioOnlyTranscription: $audioOnlyTranscription.")
        }

        val request = audioOnlyTranscription
            .toSimpleGptAudioTranscriptionV1RequestRemoteDataModel()
        dataSource
            .requestAudioTranscription(request)
            .catch {
                LLog.w(it)
                throw it
            }
            .collect { response ->
                val resultTranscription = response.toGptAudioTranscriptionEntity(
                    audioOnlyTranscription.audioFile)
                emit(resultTranscription)
                LLog.v("resultTranscription: $resultTranscription.")
            }
        LLog.v("audioOnlyTranscription: $audioOnlyTranscription.")
    }.flowOn(Dispatchers.IO)

    fun requestAudioToEnglishTextTranslations(
        audioOnlyTranslation: GptAudioToEnglishTextTranslation
    ) = flow {
        if (!audioOnlyTranslation.existAudioFile) {
            val errorMessage = "audioOnlyTranslation.audioFile does not exist:" +
                    " audioOnlyTranslation: $audioOnlyTranslation."
            LLog.w(errorMessage)
            throw IllegalArgumentException(errorMessage)
        }
        if (!audioOnlyTranslation.isAudioOnly) {
            LLog.i("audioOnlyTranslation is not audio only. Its text will be ignored." +
                    " audioOnlyTranslation: $audioOnlyTranslation.")
        }

        val request = audioOnlyTranslation
            .toSimpleGptAudioToEnglishTextTranslationV1RequestRemoteDataModel()
        dataSource
            .requestAudioToEnglishTextTranslations(request)
            .catch {
                LLog.w(it)
                throw it
            }
            .collect { response ->
                val resultTranslation = response.toGptAudioToEnglishTextTranslationEntity(
                    audioOnlyTranslation.audioFile)
                emit(resultTranslation)
                LLog.v("resultTranslation: $resultTranslation.")
            }
        LLog.v("audioOnlyTranslation: $audioOnlyTranslation.")
    }.flowOn(Dispatchers.IO)
}