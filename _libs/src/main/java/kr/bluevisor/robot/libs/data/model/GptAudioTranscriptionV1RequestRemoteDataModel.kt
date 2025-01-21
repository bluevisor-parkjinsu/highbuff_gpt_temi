package kr.bluevisor.robot.libs.data.model

import kr.bluevisor.robot.libs.data.ModelMapper
import com.squareup.moshi.Moshi
import enn.libs.and.llog.LLog
import okhttp3.MultipartBody
import java.io.File

data class GptAudioTranscriptionV1RequestRemoteDataModel(
    val file: File,
    val model: String = FIELD_VALUE__MODEL__WHISPER_1,
    val language: String? = null,
    val prompt: String? = null,
    val response_format: String? = null,
    val temperature: Double? = null,
    val timestamp_granularities: List<Any>? = null,
) {
    fun toMultiSubPartForFile(mimeType: String = "audio/*"): MultipartBody.Part {
        return ModelMapper.toMultiSubPartFromFile("file", file, mimeType)
    }

    fun toMultiSubPartListExcludeFile(moshi: Moshi? = null): List<MultipartBody.Part> {
        val multiSubPartList = buildList {
            add(MultipartBody.Part.createFormData("model", model))
            language?.let {
                add(MultipartBody.Part.createFormData("language", it))
            }
            prompt?.let {
                add(MultipartBody.Part.createFormData("prompt", it))
            }
            response_format?.let {
                add(MultipartBody.Part.createFormData("response_format", it))
            }
            temperature?.let {
                add(MultipartBody.Part.createFormData("temperature", it.toString()))
            }
            timestamp_granularities?.let {
                val moshiAdapter = moshi?.adapter(List::class.java) ?: run {
                    LLog.w("This needs moshi but it is null.")
                    return@let
                }

                add(MultipartBody.Part.createFormData(
                    "timestamp_granularities",
                    moshiAdapter.toJson(it))
                )
            }
        }
        LLog.v("moshi: $moshi, multiSubPartList: $multiSubPartList.")
        return multiSubPartList
    }

    companion object {
        const val FIELD_VALUE__MODEL__WHISPER_1 = "whisper-1"
    }
}