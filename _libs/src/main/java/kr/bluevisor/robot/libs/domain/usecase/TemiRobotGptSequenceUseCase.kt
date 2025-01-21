package kr.bluevisor.robot.libs.domain.usecase

import enn.libs.and.llog.LLog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobotSpeech
import kr.bluevisor.robot.libs.data.repository.GptChatRepository
import kr.bluevisor.robot.libs.data.repository.GptStoredChatRepository
import kr.bluevisor.robot.libs.domain.entity.GptChatCompletion
import kr.bluevisor.robot.libs.domain.entity.GptToolFunction
import javax.inject.Inject
import javax.inject.Singleton

open class BaseTemiRobotGptSequenceUseCase(
    private val temiRobot: TemiRobot,
    private val gptToolFunctionUseCaseList: List<GptToolFunctionUseCase>,
    protected val gptStoredChatUseCase: GptStoredChatUseCase,
) {
    protected open val initialGptSystemContext =
        """
            당신은 테미('Temi') 라는 이름을 가진 로봇입니다. 주문에 따라 대화하고 움직이세요.
            명령은 반드시 항상 한 번에 하나의 명령만 실행하세요.
        """.trimIndent()

    open fun initEnvironment(needRegisterGptToolFunctionList: List<GptToolFunction>) {
        gptStoredChatUseCase.storeChatCompletion(GptChatCompletion(
            messageList = listOf(
                GptChatCompletion.Message(
                    role = GptChatCompletion.ChatRoleType.SYSTEM,
                    contentList = listOf(Pair(
                        GptChatCompletion.Message.ContentType.TEXT,
                        initialGptSystemContext
                    ))
                )
            ),
            functionList = needRegisterGptToolFunctionList
        ))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    open fun speechToOrder(isOneShat: Boolean = true) = callbackFlow {
        LLog.v("isOneShot: $isOneShat.")
        temiRobot.wakeUpFlow(false)
            .map { resultMap -> resultMap[TemiRobotSpeech.ASR_LISTENER__ASR_RESULT].toString() }
            .flatMapConcat { speechText ->
                val responseChatMessageList = gptStoredChatUseCase.sendChatMessage(
                    GptChatCompletion.Message(
                        role = GptChatCompletion.ChatRoleType.USER,
                        contentList = listOf(
                            GptChatCompletion.Message.ContentType.TEXT to speechText
                        )
                    )
                )
                LLog.v("speechText: $speechText.")
                responseChatMessageList
            }
            .onEach { responseChatMessageList ->
                if (responseChatMessageList.size != 1) {
                    LLog.i("responseChatMessageList.size is not equal to 1: " +
                            "responseChatMessageList.size: ${responseChatMessageList.size}.")
                }
                LLog.v("responseChatMessageList: $responseChatMessageList.")
            }
            .map { responseChatMessageList -> responseChatMessageList.last() }
            .catch { cause ->
                LLog.w(cause)
                throw cause
            }
            .collect { responseInstructionMessage ->
                interpretInstruction(this, responseInstructionMessage)
                if (isOneShat) close()
                LLog.v("responseInstructionMessage: $responseInstructionMessage," +
                        " isOneShot: $isOneShat.")
            }
        awaitClose { LLog.v("awaitClose() called: isOneShot: $isOneShat.") }
    }

    private suspend fun interpretInstruction(
        producerScope: ProducerScope<Any?>,
        instructionMessage: GptChatCompletion.Message
    ) {
        val messageContentList = instructionMessage.contentList
        if (messageContentList.size != 1) {
            LLog.w("messageContentList.size is not equal to 1: " +
                    "messageContentList.size: ${messageContentList.size}.")
        }

        val (_, contentText) = messageContentList.single()
        if (contentText.isNotBlank()) {
            temiRobot.askQuestionSimpleFlow(contentText)
                .launchIn(producerScope).join()
            LLog.v("contentText: $contentText.")
        } else { LLog.w("contentText is blank. Temi will not speak a message.") }

        val functionCallbackList = instructionMessage.functionCallList.map { functionCall ->
            var returnValueText = RETURN_VALUE__FAILED
            gptToolFunctionUseCaseList.forEach { useCase ->
                useCase.interpretFunctionCall(functionCall)
                    ?.catch { cause -> LLog.w(cause) }
                    ?.collect { signatureReturnValueText ->
                        returnValueText = signatureReturnValueText?.toString() ?: returnValueText
                        if (producerScope.trySend(signatureReturnValueText).isFailure) {
                            LLog.w("trySend() is failed.")
                        }
                        LLog.v("signatureReturnValueText: $signatureReturnValueText.")
                    }
            }
            LLog.v("functionCall: $functionCall, returnValueText: $returnValueText.")
            functionCall.id to returnValueText
        }

        if (functionCallbackList.isNotEmpty()) {
            gptStoredChatUseCase.sendToolCallbackChatMessages(functionCallbackList)
                .collect { responseChatMessageList ->
                    if (responseChatMessageList.size != 1) {
                        LLog.i("responseChatMessageList.size is not equal to 1: " +
                                "responseChatMessageList.size: ${responseChatMessageList.size}.")
                    }

                    interpretInstruction(producerScope, responseChatMessageList.last())
                    LLog.v("responseChatMessageList: $responseChatMessageList.")
                }
        }
        LLog.v("instruction: $instructionMessage.")
    }

    companion object {
        private const val RETURN_VALUE__FAILED = "Failed"
    }
}

@Singleton
class TemiRobotGptSequenceUseCase @Inject constructor(
    temiRobot: TemiRobot,
    temiRobotGptToolFunctionUseCase: TemiRobotGptToolFunctionUseCase,
    nativeRobotGptToolFunctionUseCase: NativeRobotGptToolFunctionUseCase,
    gptToGptToolFunctionUseCase: GptToGptToolFunctionUseCase,
    gptChatRepository: GptChatRepository
) : BaseTemiRobotGptSequenceUseCase(
    temiRobot = temiRobot,
    gptToolFunctionUseCaseList = listOf(
        temiRobotGptToolFunctionUseCase,
        nativeRobotGptToolFunctionUseCase,
        gptToGptToolFunctionUseCase
    ),
    gptStoredChatUseCase = GptStoredChatUseCase(GptStoredChatRepository(gptChatRepository))
)