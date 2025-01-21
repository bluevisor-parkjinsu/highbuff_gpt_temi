package kr.bluevisor.robot.libs.data.repository

import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kr.bluevisor.robot.libs.data.datasource.GptRemoteDataSource
import kr.bluevisor.robot.libs.data.toGptChatCompletionV1RequestRemoteDataModel
import kr.bluevisor.robot.libs.data.toGptChatMessageEntityList
import kr.bluevisor.robot.libs.domain.entity.GptChatCompletion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GptChatRepository @Inject constructor(
    private val dataSource: GptRemoteDataSource
) {
    fun requestToSendChatCompletion(newChatCompletion: GptChatCompletion) = flow {
        val request =
            newChatCompletion.toGptChatCompletionV1RequestRemoteDataModel()
        dataSource.requestToSendChatCompletion(request)
            .catch {
                LLog.w(it)
                throw it
            }
            .collect { response ->
                val resultChatMessageList = response.toGptChatMessageEntityList()
                emit(resultChatMessageList)
                LLog.v("resultChatMessageList: $resultChatMessageList.")
            }
        LLog.v("newChatCompletion: $newChatCompletion.")
    }.flowOn(Dispatchers.IO)

    fun requestToSendChatMessages(newChatMessages: List<GptChatCompletion.Message>) =
        requestToSendChatCompletion(GptChatCompletion(newChatMessages))

    fun requestToSendChatMessage(newMessage: GptChatCompletion.Message) =
        requestToSendChatCompletion(GptChatCompletion(listOf(newMessage)))
}