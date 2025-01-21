package kr.bluevisor.robot.libs.data

import kr.bluevisor.robot.libs.data.model.GptAudioSpeechV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioToEnglishTextTranslationV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioTranscriptionV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioTranscriptionVerboseV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptChatCompletionChunkV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptChatCompletionV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptChatCompletionV1ResponseRemoteDataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface GptApis {
    @Headers("Content-Type: application/json")
    @POST(GptApiContract.URL_PATH__V1_CHAT_COMPLETIONS)
    fun requestV1ChatCompletions(
        @Body body: GptChatCompletionV1RequestRemoteDataModel
    ): Call<GptChatCompletionV1ResponseRemoteDataModel>

    @Headers("Content-Type: application/json")
    @POST(GptApiContract.URL_PATH__V1_CHAT_COMPLETIONS)
    fun requestV1ChatCompletionsToReceiveChunk(
        @Body body: GptChatCompletionV1RequestRemoteDataModel
    ): Call<GptChatCompletionChunkV1ResponseRemoteDataModel>

    @Headers("Content-Type: application/json")
    @POST(GptApiContract.URL_PATH__V1_AUDIO_SPEECH)
    fun requestV1AudioSpeech(
        @Body body: GptAudioSpeechV1RequestRemoteDataModel
    ): Call<ResponseBody>

    @Multipart
    @POST(GptApiContract.URL_PATH__V1_AUDIO_TRANSCRIPTIONS)
    fun requestV1AudioTranscriptions(
        @Part audioFile: MultipartBody.Part,
        @Part requestBody: List<MultipartBody.Part>
    ): Call<GptAudioTranscriptionV1ResponseRemoteDataModel>

    @Multipart
    @POST(GptApiContract.URL_PATH__V1_AUDIO_TRANSCRIPTIONS)
    fun requestV1AudioTranscriptionsToReceiveVerbose(
        @Part audioFile: MultipartBody.Part,
        @Part requestBody: List<MultipartBody.Part>
    ): Call<GptAudioTranscriptionVerboseV1ResponseRemoteDataModel>

    @Multipart
    @POST(GptApiContract.URL_PATH__V1_AUDIO_TRANSLATIONS)
    fun requestV1AudioToEnglishTextTranslations(
        @Part audioFile: MultipartBody.Part,
        @Part requestBody: List<MultipartBody.Part>,
    ): Call<GptAudioToEnglishTextTranslationV1ResponseRemoteDataModel>
}

interface GptRawApis {
    @POST(GptApiContract.URL_PATH__V1_CHAT_COMPLETIONS)
    fun requestV1ChatCompletions(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String,
        @Body body: RequestBody
    ): Call<ResponseBody>

    @POST(GptApiContract.URL_PATH__V1_AUDIO_SPEECH)
    fun requestV1AudioSpeech(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String,
        @Body body: RequestBody
    ): Call<ResponseBody>

    @Multipart
    @POST(GptApiContract.URL_PATH__V1_AUDIO_TRANSCRIPTIONS)
    fun requestV1AudioTranscriptions(
        @Header("Authorization") authorization: String,
        @Part audioFile: MultipartBody.Part,
        @Part("model") model: RequestBody
    ): Call<ResponseBody>

    @Multipart
    @POST(GptApiContract.URL_PATH__V1_AUDIO_TRANSLATIONS)
    fun requestV1AudioToEnglishTextTranslations(
        @Header("Authorization") authorization: String,
        @Part audioFile: MultipartBody.Part,
        @Part("model") model: RequestBody
    ): Call<ResponseBody>
}