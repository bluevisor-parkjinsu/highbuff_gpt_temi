package kr.bluevisor.robot.libs.data

import enn.libs.and.llog.LLog
import kr.bluevisor.robot.libs.data.model.GptAudioSpeechV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioToEnglishTextTranslationV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioToEnglishTextTranslationV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioTranscriptionV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptAudioTranscriptionV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptChatCompletionV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptChatCompletionV1ResponseRemoteDataModel
import kr.bluevisor.robot.libs.data.model.GptToolFunctionSubRequestRemoteDataModel
import kr.bluevisor.robot.libs.domain.entity.GptAudioSpeech
import kr.bluevisor.robot.libs.domain.entity.GptAudioToEnglishTextTranslation
import kr.bluevisor.robot.libs.domain.entity.GptAudioTranscription
import kr.bluevisor.robot.libs.domain.entity.GptChatCompletion
import kr.bluevisor.robot.libs.domain.entity.GptToolFunction
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

object ModelMapper {
    fun toGptChatCompletionV1RequestRemoteDataModel(
        chatCompletionEntity: GptChatCompletion
    ): GptChatCompletionV1RequestRemoteDataModel {
        fun toContentPartRemoteModelList(
            contentEntityList: List<Pair<GptChatCompletion.Message.ContentType, String>>
        ): List<Any> {
            return contentEntityList.map { (contentType, contentText) ->
                when (contentType) {
                    GptChatCompletion.Message.ContentType.TEXT ->
                        GptChatCompletionV1RequestRemoteDataModel.UserMessage.TextContentPart(
                            text = contentText
                        )
                    GptChatCompletion.Message.ContentType.IMAGE ->
                        GptChatCompletionV1RequestRemoteDataModel.UserMessage.ImageContentPart(
                            image_url = GptChatCompletionV1RequestRemoteDataModel.UserMessage
                                .ImageContentPart.ImageUrl(
                                    url = contentText
                                )
                        )
                }
            }
        }

        fun toToolCallsPartRemoteModelList(
            functionCallList: List<GptToolFunction.Call>
        ): List<GptChatCompletionV1RequestRemoteDataModel.AssistantMessage.ToolCalls>? {
            return functionCallList.map { functionCall ->
                GptChatCompletionV1RequestRemoteDataModel.AssistantMessage.ToolCalls(
                    id = functionCall.id,
                    function = GptChatCompletionV1RequestRemoteDataModel.AssistantMessage
                        .ToolCalls.Function(
                            name = functionCall.functionName,
                            arguments = functionCall.parameterArgumentMap.entries
                                .fold(JSONObject()) { jsonObject, (key, value) ->
                                    jsonObject.put(key, value.toString())
                                }.toString()
                    )
                )
            }.let { it.ifEmpty { null } }
        }

        val remoteRequest = GptChatCompletionV1RequestRemoteDataModel(
            model = chatCompletionEntity.model.remoteDataFieldValue,
            messages = chatCompletionEntity.messageList
                .map { messageEntity ->
                    when (messageEntity.role) {
                        GptChatCompletion.ChatRoleType.SYSTEM -> {
                            GptChatCompletionV1RequestRemoteDataModel.SystemMessage(
                                content = toContentPartRemoteModelList(
                                    messageEntity.contentList)
                            )
                        }
                        GptChatCompletion.ChatRoleType.USER -> {
                            GptChatCompletionV1RequestRemoteDataModel.UserMessage(
                                content = toContentPartRemoteModelList(
                                    messageEntity.contentList)
                            )
                        }
                        GptChatCompletion.ChatRoleType.ASSISTANT -> {
                            GptChatCompletionV1RequestRemoteDataModel.AssistantMessage(
                                content = toContentPartRemoteModelList(
                                    messageEntity.contentList),
                                tool_calls = toToolCallsPartRemoteModelList(
                                    messageEntity.functionCallList)
                            )
                        }
                        GptChatCompletion.ChatRoleType.TOOL -> {
                            val functionCallback = messageEntity.functionCallback
                                ?: throw IllegalArgumentException(
                                    "messageEntity.functionCallback is null.")
                            GptChatCompletionV1RequestRemoteDataModel.ToolMessage(
                                content = functionCallback.returnValue,
                                tool_call_id = functionCallback.id
                            )
                        }
                    }
                }
                .toList(),
            tools = chatCompletionEntity.functionList
                .map { GptToolFunctionSubRequestRemoteDataModel(
                    function = toGptToolFunctionSubRequestRemoteDataModel(it)
                )}
                .takeUnless { it.isEmpty() },
            tool_choice =
            if (chatCompletionEntity.functionCallingOnly) {
                GptChatCompletionV1RequestRemoteDataModel.FIELD_VALUE__TOOL_CHOICE__REQUIRED
            } else { null }
        )

        LLog.v("chatCompletionEntity: $chatCompletionEntity, remoteRequest: $remoteRequest.")
        return remoteRequest
    }

    fun toSimpleGptAudioSpeechV1RequestRemoteDataModel(
        speechEntity: GptAudioSpeech
    ): GptAudioSpeechV1RequestRemoteDataModel {
        val remoteRequest = GptAudioSpeechV1RequestRemoteDataModel(
            model = speechEntity.model.remoteDataFieldValue,
            input = speechEntity.input,
            voice = speechEntity.voice.name.lowercase()
        )

        LLog.v("speechEntity: $speechEntity, remoteRequest: $remoteRequest.")
        return remoteRequest
    }

    fun toSimpleGptAudioTranscriptionV1RequestRemoteDataModel(
        transcriptionEntity: GptAudioTranscription
    ): GptAudioTranscriptionV1RequestRemoteDataModel {
        val remoteRequest = GptAudioTranscriptionV1RequestRemoteDataModel(
            file = transcriptionEntity.audioFile
        )

        LLog.v("transcriptionEntity: $transcriptionEntity, remoteRequest: $remoteRequest.")
        return remoteRequest
    }

    fun toSimpleGptAudioToEnglishTextTranslationV1RequestRemoteDataModel(
        translationEntity: GptAudioToEnglishTextTranslation
    ): GptAudioToEnglishTextTranslationV1RequestRemoteDataModel {
        val remoteRequest = GptAudioToEnglishTextTranslationV1RequestRemoteDataModel(
            file = translationEntity.audioFile
        )

        LLog.v("translationEntity: $translationEntity, remoteRequest: $remoteRequest.")
        return remoteRequest
    }

    fun toGptToolFunctionSubRequestRemoteDataModel(
        toolFunctionEntity: GptToolFunction
    ): GptToolFunctionSubRequestRemoteDataModel.Function {
        val remoteRequest = GptToolFunctionSubRequestRemoteDataModel.Function(
            name = toolFunctionEntity.name,
            description = toolFunctionEntity.description,
            parameters = GptToolFunctionSubRequestRemoteDataModel.Function.Parameters(
                properties = toolFunctionEntity.parameterList
                    .fold(mutableMapOf()) { map, parameterEntity ->
                        map[parameterEntity.name] =
                            GptToolFunctionSubRequestRemoteDataModel.Function.Parameters
                                .Property(
                                    type = parameterEntity.type,
                                    enum = with(parameterEntity.argumentRange) {
                                        ifEmpty { null }
                                    },
                                    description = parameterEntity.description
                                )
                        map
                    },
                required = toolFunctionEntity.parameterList
                    .asSequence()
                    .filter { it.required }
                    .map { it.name }
                    .toList()
            )
        )

        LLog.v("toolFunctionEntity: $toolFunctionEntity, remoteRequest: $remoteRequest.")
        return remoteRequest
    }

    fun toGptChatMessageEntityList(
        remoteResponse: GptChatCompletionV1ResponseRemoteDataModel
    ): List<GptChatCompletion.Message> {
        val entityList = remoteResponse.choices
            .asSequence()
            .sortedBy { it.index }
            .map { choice -> GptChatCompletion.Message(
                role = GptChatCompletion.ChatRoleType.valueOf(choice.message.role.uppercase()),
                contentList = listOf(Pair(
                    GptChatCompletion.Message.ContentType.TEXT,
                    choice.message.content ?: "")
                ),
                functionCallList = choice.message.tool_calls
                    .map { call -> GptToolFunction.Call(
                        id = call.id,
                        functionName = call.function.name,
                        parameterArgumentMap = JSONObject(call.function.arguments)
                            .let { jsonObject -> jsonObject.keys()
                                .asSequence()
                                .map { key -> key to jsonObject[key] }
                                .fold(mutableMapOf()) { map, element ->
                                    map.apply {
                                        put(element.first, element.second)
                                    }
                                }
                            }
                    )}
            )}
            .toList()

        LLog.v("remoteResponse: $remoteResponse, entityList: $entityList.")
        return entityList
    }

    fun toGptAudioTranscriptionEntity(
        remoteResponse: GptAudioTranscriptionV1ResponseRemoteDataModel,
        audioFile: File
    ): GptAudioTranscription {
        val entity = GptAudioTranscription(audioFile = audioFile, text = remoteResponse.text)
        LLog.v("remoteResponse: $remoteResponse, audioFile: $audioFile, entity: $entity.")
        return entity
    }

    fun toGptAudioToEnglishTextTranslationEntity(
        remoteResponse: GptAudioToEnglishTextTranslationV1ResponseRemoteDataModel,
        audioFile: File
    ): GptAudioToEnglishTextTranslation {
        val entity =
            GptAudioToEnglishTextTranslation(audioFile = audioFile, text = remoteResponse.text)
        LLog.v("remoteResponse: $remoteResponse, audioFile: $audioFile, entity: $entity.")
        return entity
    }

    fun toMultiSubPartFromFile(
        fieldName: String,
        file: File,
        mimeType: String
    ): MultipartBody.Part {
        val fileRequestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
        val multiSubPart = MultipartBody.Part.createFormData(
            fieldName, file.name, fileRequestBody)
        LLog.v("fieldName: $fieldName, file: $file, mimeType: $mimeType.")
        return multiSubPart
    }
}

fun GptChatCompletion.toGptChatCompletionV1RequestRemoteDataModel() =
    ModelMapper.toGptChatCompletionV1RequestRemoteDataModel(this)

fun GptAudioSpeech.toSimpleGptAudioSpeechV1RequestRemoteDataModel() =
    ModelMapper.toSimpleGptAudioSpeechV1RequestRemoteDataModel(this)

fun GptAudioTranscription.toSimpleGptAudioTranscriptionV1RequestRemoteDataModel() =
    ModelMapper.toSimpleGptAudioTranscriptionV1RequestRemoteDataModel(this)

fun GptAudioToEnglishTextTranslation
    .toSimpleGptAudioToEnglishTextTranslationV1RequestRemoteDataModel() =
    ModelMapper.toSimpleGptAudioToEnglishTextTranslationV1RequestRemoteDataModel(this)

fun GptToolFunction.toGptToolFunctionSubRequestRemoteDataModel() =
    ModelMapper.toGptToolFunctionSubRequestRemoteDataModel(this)

fun GptChatCompletionV1ResponseRemoteDataModel.toGptChatMessageEntityList() =
    ModelMapper.toGptChatMessageEntityList(this)

fun GptAudioTranscriptionV1ResponseRemoteDataModel.toGptAudioTranscriptionEntity(audioFile: File) =
    ModelMapper.toGptAudioTranscriptionEntity(this, audioFile)

fun GptAudioToEnglishTextTranslationV1ResponseRemoteDataModel
    .toGptAudioToEnglishTextTranslationEntity(audioFile: File) =
    ModelMapper.toGptAudioToEnglishTextTranslationEntity(this, audioFile)