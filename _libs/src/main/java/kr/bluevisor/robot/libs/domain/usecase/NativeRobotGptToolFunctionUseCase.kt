package kr.bluevisor.robot.libs.domain.usecase

import enn.libs.and.llog.LLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.bluevisor.robot.libs.core.platform.media.process.MediaContentResolvers
import kr.bluevisor.robot.libs.core.platform.robot.NativeRobot
import kr.bluevisor.robot.libs.domain.entity.GptToolFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeRobotGptToolFunctionUseCase @Inject constructor(
    private val nativeRobot: NativeRobot
) : GptToolFunctionUseCase {
    override val functionList: List<GptToolFunction> = listOf(newTakePhotoFunction())

    fun newTakePhotoFunction() = GptToolFunction(
        name = TAKE_PHOTO__FUNCTION__NAME,
        description = TAKE_PHOTO__FUNCTION__DESCRIPTION
    )

    fun interpretTakePhotoFunctionCall(
        functionCall: GptToolFunction.Call
    ): Flow<Long?> {
        checkAndThrowDoesNotSameFunctionNameException(TAKE_PHOTO__FUNCTION__NAME, functionCall)
        LLog.v("functionCall: $functionCall.")

        return nativeRobot.camera.takePhotoWithPreviewFlow()
            .map { imageUri ->
                if (imageUri == null) return@map null
                MediaContentResolvers.getIdFromLastPathSegmentOfContentUri(imageUri)
            }
    }

    fun makeTakePhotoFunctionCallSignatureReturnValueText(
        callbackArgument: Any?
    ) = makeFunctionSignatureReturnValueText(
        TAKE_PHOTO__FUNCTION__NAME,
        TAKE_PHOTO__FUNCTION__DESCRIPTION,
        callbackArgument
    )

    override fun interpretFunctionCall(
        functionCall: GptToolFunction.Call,
        needToMakeSignatureReturnValueText: Boolean
    ): Flow<*>? {
        val resultFlow = when (functionCall.functionName) {
            TAKE_PHOTO__FUNCTION__NAME ->
                processToMakeFunctionSignatureReturnValueTextIf(
                    needToMakeSignatureReturnValueText,
                    interpretTakePhotoFunctionCall(functionCall),
                    ::makeTakePhotoFunctionCallSignatureReturnValueText
                )
            else -> {
                LLog.v("Not found matched function name: functionCall: $functionCall.")
                return null
            }
        }
        LLog.v("functionCall: $functionCall.")
        return resultFlow
    }

    companion object : StaticGptToolFunctionUseCases() {
        const val TAKE_PHOTO__FUNCTION__NAME = "takePhoto"
        const val TAKE_PHOTO__FUNCTION__DESCRIPTION =
            "Take a photo of the front, while simultaneously showing the front-facing camera preview window on the front of the display."
        const val TAKE_PHOTO__FUNCTION_RETURN__MEDIA_ID__NAME = "mediaId"
        const val TAKE_PHOTO__FUNCTION_RETURN__MEDIA_ID__DESCRIPTION =
            "The ID of the photo you took."
    }
}