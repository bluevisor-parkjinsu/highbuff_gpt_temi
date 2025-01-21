package kr.bluevisor.robot.libs.domain.entity

import enn.libs.and.llog.LLog
import kr.bluevisor.robot.libs.data.model.GptChatCompletionV1RequestRemoteDataModel

data class GptChatCompletion(
    val messageList: List<Message>,
    val model: ModelType = ModelType.GPT_4O_MINI,
    val functionList: List<GptToolFunction> = emptyList(),
    val functionCallingOnly: Boolean = false
) {
    enum class ModelType(val remoteDataFieldValue: String) {
        GPT_4O(GptChatCompletionV1RequestRemoteDataModel.FIELD_VALUE__MODEL__GPT_4O),
        GPT_4O_MINI(GptChatCompletionV1RequestRemoteDataModel.FIELD_VALUE__MODEL__GPT_4O_MINI)
    }

    enum class ChatRoleType {
        SYSTEM,
        USER,
        ASSISTANT,
        TOOL
    }

    data class Message(
        val role: ChatRoleType,
        val contentList: List<Pair<ContentType, String>>,
        val functionCallList: List<GptToolFunction.Call> = emptyList(),
        val functionCallback: GptToolFunction.Callback? = null
    ) {
        enum class ContentType {
            TEXT,
            IMAGE
        }
    }

    fun newAppended(newMessageList: List<Message>): GptChatCompletion {
        val chatCompletion = copy(
            messageList = messageList
                .toMutableList()
                .apply { addAll(newMessageList) }
        )
        LLog.v("newMessageList: $newMessageList, chatCompletion: $chatCompletion.")
        return chatCompletion
    }
}