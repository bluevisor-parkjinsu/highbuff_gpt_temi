package kr.bluevisor.robot.libs.domain.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import enn.libs.and.llog.LLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.bluevisor.robot.libs.core.platform.media.process.MediaContentResolvers
import kr.bluevisor.robot.libs.domain.entity.GptToolFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GptToGptToolFunctionUseCase @Inject constructor(
    private val gptChatUseCase: GptChatUseCase,
    @ApplicationContext private val context: Context
) : GptToolFunctionUseCase {
    override val functionList: List<GptToolFunction> = listOf(newAnalyzePhotoFunction())

    fun newAnalyzePhotoFunction() = GptToolFunction(
        name = ANALYZE_PHOTO__FUNCTION__NAME,
        description = ANALYZE_PHOTO__FUNCTION__DESCRIPTION,
        parameterList = listOf(
            GptToolFunction.Parameter(
                name = ANALYZE_PHOTO__FUNCTION_PARAMS__0__MEDIA_ID__NAME,
                description = ANALYZE_PHOTO__FUNCTION_PARAMS__0__MEDIA_ID__DESCRIPTION,
                required = true
            ),
            GptToolFunction.Parameter(
                name = ANALYZE_PHOTO__FUNCTION_PARAMS__1__REQUIREMENT_COMMENT__NAME,
                description = ANALYZE_PHOTO__FUNCTION_PARAMS__1__REQUIREMENT_COMMENT__DESCRIPTION,
                required = true
            )
        )
    )

    fun interpretAnalyzePhotoFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<String> {
        checkAndThrowDoesNotSameFunctionNameException(ANALYZE_PHOTO__FUNCTION__NAME, functionCall)
        LLog.v("functionCall: $functionCall.")

        val imageUri = MediaContentResolvers.getContentUriById(
            id = functionCall
                .parameterArgumentMap[ANALYZE_PHOTO__FUNCTION_PARAMS__0__MEDIA_ID__NAME]
                .toString().toLong(),
            contextResolver = context.contentResolver
        )
        val responseChatMessageFlow = gptChatUseCase.sendUserChatMessageWithImageUris(
            message = functionCall
                .parameterArgumentMap[ANALYZE_PHOTO__FUNCTION_PARAMS__1__REQUIREMENT_COMMENT__NAME]
                .toString(),
            imageContentUriList = listOf(imageUri),
            contentResolver = context.contentResolver
        )
        return responseChatMessageFlow
            .map { responseChatMessageList ->
                val (_, content) = responseChatMessageList.single().contentList.single()
                content
            }
    }

    fun makeAnalyzePhotoFunctionCallSignatureReturnValueText(
        callbackArgument: Any?
    ) = makeFunctionSignatureReturnValueText(
        ANALYZE_PHOTO__FUNCTION__NAME,
        ANALYZE_PHOTO__FUNCTION__DESCRIPTION,
        callbackArgument
    )

    override fun interpretFunctionCall(
        functionCall: GptToolFunction.Call,
        needToMakeSignatureReturnValueText: Boolean
    ): Flow<*>? {
        val resultFlow = when (functionCall.functionName) {
            ANALYZE_PHOTO__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretAnalyzePhotoFunctionCall(functionCall),
                    ::makeAnalyzePhotoFunctionCallSignatureReturnValueText
                )
            else -> {
                LLog.v("Not found matched function name: functionCall: $functionCall.")
                null
            }
        }
        LLog.v("functionCall: $functionCall.")
        return resultFlow
    }

    companion object : StaticGptToolFunctionUseCases() {
        const val ANALYZE_PHOTO__FUNCTION__NAME = "analyzePhoto"
        const val ANALYZE_PHOTO__FUNCTION__DESCRIPTION =
            "Analyze a given photo and return a rating. If you pass a requirement-comment with the photo, it will proceed with the evaluation as the requirement-comment requires."
        const val ANALYZE_PHOTO__FUNCTION_PARAMS__0__MEDIA_ID__NAME = "mediaId"
        const val ANALYZE_PHOTO__FUNCTION_PARAMS__0__MEDIA_ID__DESCRIPTION =
            "An ID value that points to a stored image, such as a photo taken."
        const val ANALYZE_PHOTO__FUNCTION_PARAMS__1__REQUIREMENT_COMMENT__NAME = "requirementComment"
        const val ANALYZE_PHOTO__FUNCTION_PARAMS__1__REQUIREMENT_COMMENT__DESCRIPTION =
            "A comment with a requirement about image analysis."
        const val ANALYZE_PHOTO__FUNCTION_RETURN__EVALUATION_COMMENT__NAME = "evaluationComment"
        const val ANALYZE_PHOTO__FUNCTION_RETURN__EVALUATION_COMMENT__DESCRIPTION = "A rating comment for the photo you received.."
    }
}