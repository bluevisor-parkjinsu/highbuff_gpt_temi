package kr.bluevisor.robot.libs.data.repository

import kr.bluevisor.robot.libs.domain.entity.GptChatCompletion
import dagger.hilt.android.scopes.ViewModelScoped
import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ViewModelScoped
class GptStoredChatRepository @Inject constructor(
    private val chatRepository: GptChatRepository
) {
    private val _chatCompletionFlow = MutableStateFlow(GptChatCompletion(emptyList()))
    val chatCompletionFlow: StateFlow<GptChatCompletion> = _chatCompletionFlow

    fun requestToSendChatCompletion(
        newChatCompletion: GptChatCompletion,
        needStoring: Boolean = true
    ) = flow {
        if (needStoring) storeChatCompletion(newChatCompletion)

        chatRepository.requestToSendChatCompletion(newChatCompletion)
            .catch {
                LLog.w(it)
                throw it
            }
            .collect { resultChatMessageList ->
                if (needStoring) storeChatMessages(resultChatMessageList)
                emit(resultChatMessageList)
                LLog.v("resultChatMessageList: $resultChatMessageList.")
            }
        LLog.v("newChatCompletion: $newChatCompletion, needStoring: $needStoring.")
    }.flowOn(Dispatchers.IO)

    fun requestToSendChatMessages(
        newChatMessages: List<GptChatCompletion.Message>,
        needStoring: Boolean = true
    ) = flow {
        val chatCompletion =
            if (needStoring)    storeChatMessages(newChatMessages)
            else                GptChatCompletion(newChatMessages)

        chatRepository.requestToSendChatCompletion(chatCompletion)
            .catch {
                LLog.w(it)
                throw it
            }
            .collect { resultChatMessageList ->
                if (needStoring) storeChatMessages(resultChatMessageList)
                emit(resultChatMessageList)
                LLog.v("resultChatMessageList: $resultChatMessageList.")
            }
        LLog.v("newChatMessages: $newChatMessages, needStoring: $needStoring.")
    }.flowOn(Dispatchers.IO)

    fun requestToSendChatMessage(
        newMessage: GptChatCompletion.Message,
        needStoring: Boolean = true
    ) = requestToSendChatMessages(listOf(newMessage), needStoring)

    fun storeChatCompletion(chatCompletion: GptChatCompletion) {
        _chatCompletionFlow.value = chatCompletion
        LLog.v("chatCompletion: $chatCompletion.")
    }

    fun storeChatMessages(newChatMessages: List<GptChatCompletion.Message>): GptChatCompletion {
        val newChatCompletion = _chatCompletionFlow.value.newAppended(newChatMessages)
        _chatCompletionFlow.value = newChatCompletion

        LLog.v("newChatMessages: $newChatMessages.")
        return newChatCompletion
    }

    fun storeChatMessage(newMessage: GptChatCompletion.Message): GptChatCompletion {
        LLog.v("newMessage: $newMessage.")
        return storeChatMessages(listOf(newMessage))
    }
}