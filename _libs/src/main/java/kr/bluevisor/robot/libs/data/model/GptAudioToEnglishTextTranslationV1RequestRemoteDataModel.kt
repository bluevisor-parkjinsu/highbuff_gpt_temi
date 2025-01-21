package kr.bluevisor.robot.libs.data.model

import kr.bluevisor.robot.libs.data.ModelMapper
import enn.libs.and.llog.LLog
import okhttp3.MultipartBody
import java.io.File

data class GptAudioToEnglishTextTranslationV1RequestRemoteDataModel(
    val file: File,
    val model: String = FIELD_VALUE__MODEL__WHISPER_1,
    val prompt: String? = null,
    val response_format: String? = null,
    val temperature: Int? = null,
) {
    fun toMultiSubPartForFile(mimeType: String = "audio/*"): MultipartBody.Part {
        return ModelMapper.toMultiSubPartFromFile("file", file, mimeType)
    }

    fun toMultiSubPartListExcludeFile(): List<MultipartBody.Part> {
        val multiSubPartList = buildList {
            add(MultipartBody.Part.createFormData("model", model))
            prompt?.let {
                add(MultipartBody.Part.createFormData("prompt", it))
            }
            response_format?.let {
                add(MultipartBody.Part.createFormData("response_format", it))
            }
            temperature?.let {
                add(MultipartBody.Part.createFormData("temperature", it.toString()))
            }
        }
        LLog.v("multiSubPartList: $multiSubPartList.")
        return multiSubPartList
    }

    companion object {
        const val FIELD_VALUE__MODEL__WHISPER_1 = "whisper-1"
    }
}