package kr.bluevisor.robot.libs.data.datasource

import kr.bluevisor.robot.libs.data.GptApis
import kr.bluevisor.robot.libs.data.model.GptAudioSpeechV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioSpeechV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioToEnglishTextTranslationV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioTranscriptionV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptChatCompletionV1RequestRemoteDataModel
import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GptRemoteDataSource @Inject constructor(
    private val apiService: GptApis
) {
    fun requestToSendChatCompletion(request: GptChatCompletionV1RequestRemoteDataModel) = flow {
        apiService
            .runCatching {
                requestV1ChatCompletions(request).execute()
            }
            .onSuccess { response ->
                val responseBody = response.body()
                    ?: throwErrorResponseBodyExceptionWithLog(
                        response, "requestToSendChatCompletion() failed")
                emit(responseBody)
                LLog.v("responseBody: $responseBody.")
            }
            .onFailure { exception ->
                LLog.w(exception)
                throw exception
            }
        LLog.v("request: $request.")
    }.flowOn(Dispatchers.IO)

    fun requestToCreateSpeech(request: GptAudioSpeechV1RequestRemoteDataModel) = flow {
        apiService
            .runCatching {
                requestV1AudioSpeech(request).execute()
            }
            .onSuccess { response ->
                val responseBody = response.body()
                    ?: throwErrorResponseBodyExceptionWithLog(
                        response, "requestToCreateSpeech() failed")
                emit(GptAudioSpeechV1ResponseRemoteDataModel(responseBody))
                LLog.v("responseBody: $responseBody.")
            }
            .onFailure { exception ->
                LLog.w(exception)
                throw exception
            }
        LLog.v("request: $request.")
    }.flowOn(Dispatchers.IO)

    fun requestAudioTranscription(request: GptAudioTranscriptionV1RequestRemoteDataModel) = flow {
        apiService
            .runCatching {
                requestV1AudioTranscriptions(
                    request.toMultiSubPartForFile(),
                    request.toMultiSubPartListExcludeFile()
                ).execute()
            }
            .onSuccess { response ->
                val responseBody = response.body()
                    ?: throwErrorResponseBodyExceptionWithLog(
                        response, "requestAudioTranscription() failed")
                emit(responseBody)
                LLog.v("responseBody: $responseBody.")
            }
            .onFailure { exception ->
                LLog.w(exception)
                throw exception
            }
        LLog.v("request: $request.")
    }.flowOn(Dispatchers.IO)

    fun requestAudioToEnglishTextTranslations(
        request: GptAudioToEnglishTextTranslationV1RequestRemoteDataModel
    ) = flow {
        apiService
            .runCatching {
                requestV1AudioToEnglishTextTranslations(
                    request.toMultiSubPartForFile(),
                    request.toMultiSubPartListExcludeFile())
                .execute()
            }
            .onSuccess { response ->
                val responseBody = response.body()
                    ?: throwErrorResponseBodyExceptionWithLog(
                        response, "requestV1AudioTranslations() failed")
                emit(responseBody)
                LLog.v("responseBody: $responseBody.")
            }
            .onFailure { exception ->
                LLog.w(exception)
                throw exception
            }
        LLog.v("request: $request.")
    }.flowOn(Dispatchers.IO)

    private fun throwErrorResponseBodyExceptionWithLog(
        response: Response<*>,
        prefixMessage: String
    ): Nothing {
        val errorMessage = (response.errorBody()?.string() ?: "Unknown error").let {
            "$prefixMessage:\n$it"
        }
        LLog.w(errorMessage)
        throw IllegalStateException(errorMessage)
    }
}