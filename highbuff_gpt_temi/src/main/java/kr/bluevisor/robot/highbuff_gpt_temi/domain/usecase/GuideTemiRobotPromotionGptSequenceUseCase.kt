package kr.bluevisor.robot.highbuff_gpt_temi.domain.usecase

import android.util.Log
import androidx.lifecycle.AtomicReference
import enn.libs.and.llog.LLog
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.bluevisor.robot.highbuff_gpt_temi.data.repository.PromotionEnvironmentRepository
import kr.bluevisor.robot.libs.core.platform.robot.TemiRobot
import kr.bluevisor.robot.libs.data.repository.GptChatRepository
import kr.bluevisor.robot.libs.data.repository.GptStoredChatRepository
import kr.bluevisor.robot.libs.domain.entity.GptChatCompletion
import kr.bluevisor.robot.libs.domain.usecase.BaseTemiRobotGptSequenceUseCase
import kr.bluevisor.robot.libs.domain.usecase.GptStoredChatUseCase
import kr.bluevisor.robot.libs.domain.usecase.GptToGptToolFunctionUseCase
import kr.bluevisor.robot.libs.domain.usecase.NativeRobotGptToolFunctionUseCase
import kr.bluevisor.robot.libs.domain.usecase.TemiRobotGptToolFunctionUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuideTemiRobotPromotionGptSequenceUseCase @Inject constructor(
    temiRobot: TemiRobot,
    temiRobotGptToolFunctionUseCase: TemiRobotGptToolFunctionUseCase,
    nativeRobotGptToolFunctionUseCase: NativeRobotGptToolFunctionUseCase,
    gptToGptToolFunctionUseCase: GptToGptToolFunctionUseCase,
    gptChatRepository: GptChatRepository,
    promotionEnvironmentRepository: PromotionEnvironmentRepository
) : BaseTemiRobotGptSequenceUseCase(
    temiRobot = temiRobot,
    gptToolFunctionUseCaseList = listOf(
        temiRobotGptToolFunctionUseCase,
        nativeRobotGptToolFunctionUseCase,
        gptToGptToolFunctionUseCase
    ),
    gptStoredChatUseCase = GptStoredChatUseCase(GptStoredChatRepository(gptChatRepository))
) {
    private var _initialGptSystemContext =
        AtomicReference<String>(PREPARED_INITIAL_GPT_SYSTEM_CONTEXT)
    override val initialGptSystemContext: String
        get() = _initialGptSystemContext.get()

    lateinit var questionAndAnswerListStateFlow: StateFlow<List<Pair<String, String>>>

    private val thisCoroutineScope = CoroutineScope(
        context = CoroutineName(
            "${GuideTemiRobotPromotionGptSequenceUseCase::class.java.simpleName}Scope"
        )
    )

    init {
        thisCoroutineScope.launch(Dispatchers.IO) {
            questionAndAnswerListStateFlow =
                promotionEnvironmentRepository.environmentFlow
                    .map { it.userDefinedQuestionAndAnswerList }
                    .stateIn(this)

            withContext(Dispatchers.Default) {
                questionAndAnswerListStateFlow.collect { qnaList ->
                    _initialGptSystemContext.set(
                        buildString {
                            append(PREPARED_INITIAL_GPT_SYSTEM_CONTEXT)
                            append("\n$CAPTION__QUESTION_AND_ANSWER_LIST\n")
                            qnaList.forEach { (question, answer) ->
                                append("\n- $KEYWORD__QUESTION : $question")
                                append("\n- $KEYWORD__ANSWER : $answer")
                            }
                        }
                    )
                    orderNewEnvironment()

                    LLog.v("qnaList: $qnaList," +
                            " _initializingEnvironmentContextGptSystemText:" +
                            " $_initialGptSystemContext.")
                }
            }
        }
    }

    private fun orderNewEnvironment() {
        gptStoredChatUseCase.storeChatMessage(
            GptChatCompletion.Message(
                role = GptChatCompletion.ChatRoleType.SYSTEM,
                contentList = listOf(
                    GptChatCompletion.Message.ContentType.TEXT to initialGptSystemContext
                )
            )
        )
        LLog.v("initialGptSystemContext: $initialGptSystemContext.")
    }

    override fun speechToOrder(isOneShat: Boolean): Flow<Any?> {
        return super.speechToOrder(isOneShat).onCompletion { throwable ->
            val logMessage = "speechToOrder() calling is completed."
            thisCoroutineScope.cancel(logMessage, throwable)
            LLog.print(if (throwable == null) Log.VERBOSE else Log.WARN, throwable, logMessage)
        }
    }

    companion object {
        private val PREPARED_INITIAL_GPT_SYSTEM_CONTEXT =
            """
                당신은 테미('Temi') 라는 이름을 가진 로봇입니다.
                주문에 따라 40자 내로 대화하고 움직이세요. 한 번에 하나의 명령만 실행하세요.
                당신은 주변의 사람들에게 광고와 홍보를 목적으로 합니다.
            """.trimIndent()
        private const val CAPTION__QUESTION_AND_ANSWER_LIST =
            "사람들의 질문에 대해 다음의 예시 질문-답변 목록을 토대로 하여 글자수 제한에 상관없이 답변하세요."
        private const val KEYWORD__QUESTION = "질문"
        private const val KEYWORD__ANSWER = "답변"
    }
}