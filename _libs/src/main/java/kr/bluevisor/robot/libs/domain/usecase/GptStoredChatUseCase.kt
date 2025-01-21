package kr.bluevisor.robot.libs.domain.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kr.bluevisor.robot.libs.data.repository.GptStoredChatRepository
import kr.bluevisor.robot.libs.domain.entity.GptChatCompletion
import kr.bluevisor.robot.libs.domain.entity.GptToolFunction
import javax.inject.Inject

@ViewModelScoped
class GptStoredChatUseCase @Inject constructor(private val repository: GptStoredChatRepository) {
    fun sendChatCompletion(chatCompletion: GptChatCompletion, needStoring: Boolean = true) =
        repository.requestToSendChatCompletion(chatCompletion, needStoring)

    fun sendChatMessages(messages: List<GptChatCompletion.Message>, needStoring: Boolean = true) =
        repository.requestToSendChatMessages(messages, needStoring)

    fun sendChatMessage(message: GptChatCompletion.Message, needStoring: Boolean = true) =
        repository.requestToSendChatMessage(message, needStoring)

    fun sendUserChatMessage(message: String, needStoring: Boolean = true) =
        repository.requestToSendChatMessage(
            GptChatCompletion.Message(
                role = GptChatCompletion.ChatRoleType.USER,
                contentList = listOf(Pair(GptChatCompletion.Message.ContentType.TEXT, message))
            ),
            needStoring
        )

    fun storeChatCompletion(chatCompletion: GptChatCompletion) =
        repository.storeChatCompletion(chatCompletion)

    fun storeChatMessages(messages: List<GptChatCompletion.Message>) =
        repository.storeChatMessages(messages)

    fun storeChatMessage(message: GptChatCompletion.Message) =
        repository.storeChatMessage(message)

    fun storeUserChatMessage(message: String) =
        repository.storeChatMessage(
            GptChatCompletion.Message(
                role = GptChatCompletion.ChatRoleType.USER,
                contentList = listOf(Pair(GptChatCompletion.Message.ContentType.TEXT, message))
            )
        )

    fun sendToolCallbackChatMessages(
        callbackList: List<Pair<String, String>>
    ): Flow<List<GptChatCompletion.Message>> {
        val chatMessageList = callbackList.map { (functionCallId, returnValueText) ->
            GptChatCompletion.Message(
                role = GptChatCompletion.ChatRoleType.TOOL,
                contentList = emptyList(),
                functionCallback = GptToolFunction.Callback(functionCallId, returnValueText)
            )
        }
        return repository.requestToSendChatMessages(chatMessageList)
    }

    fun sendToolCallbackChatMessage(
        callback: Pair<String, String>
    ) = sendToolCallbackChatMessages(listOf(callback))
}