package kr.bluevisor.robot.libs.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.bluevisor.robot.libs.domain.entity.GptToolFunction
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

interface GptToolFunctionUseCase {
    val functionList: List<GptToolFunction>

    fun interpretFunctionCall(
        functionCall: GptToolFunction.Call,
        needToMakeSignatureReturnValueText: Boolean = true
    ): Flow<*>?
}

open class StaticGptToolFunctionUseCases {
    val RETURN_VALUE__COMPLETED = "Completed"
    val FUNCTION_ARGUMENT_RANGE_LIST__BOOLEAN = listOf("true", "false")

    fun getParameterName(
        parameterList: List<KParameter>, parameterIndex: Int,
    ) = parameterList[parameterIndex].name
        ?: throw IllegalArgumentException(
            "The name of the parameter #$parameterIndex not found.")

    fun getParameterArgument(
        parameterIndex: Int,
        parameterList: List<KParameter>,
        parameterArgumentMap: Map<String, Any>
    ): String? {
        val parameterName =
            getParameterName(parameterList, parameterIndex)
        return parameterArgumentMap[parameterName]
            ?.toString()
            ?.takeUnless { it == "null" }
            ?.toString()
    }

    fun newGetParameterArgumentFunction(
        parameterList: List<KParameter>,
        parameterArgumentMap: Map<String, Any>
    ) = fun (parameterIndex: Int) =
        getParameterArgument(
            parameterIndex, parameterList, parameterArgumentMap)

    fun makeFunctionSignatureReturnValueText(
        valueName: String, valueDescription: String, value: Any?
    ) = "$valueName : $value, $valueName.description : $valueDescription"

    fun <T> processToMakeFunctionSignatureReturnValueTextIf(
        needProcess: Boolean = true,
        functionCallbackFlow: Flow<T>,
        makeFunctionSignatureReturnValueTextTask: (T) -> String = { RETURN_VALUE__COMPLETED }
    ): Flow<*> {
        if (!needProcess) return functionCallbackFlow
        return functionCallbackFlow.map { makeFunctionSignatureReturnValueTextTask(it) }
    }

    fun checkAndThrowDoesNotSameFunctionNameException(
        function: KFunction<*>,
        gptFunctionCall: GptToolFunction.Call
    ) {
        if (function::name.get() != gptFunctionCall.functionName) {
            throw IllegalArgumentException("Function names do not match:" +
                    " function::name.get(): ${function::name.get()}," +
                    " gptFunctionCall.functionName: ${gptFunctionCall.functionName}.")
        }
    }

    fun checkAndThrowDoesNotSameFunctionNameException(
        name: String,
        gptFunctionCall: GptToolFunction.Call
    ) {
        if (name != gptFunctionCall.functionName) {
            throw IllegalArgumentException("Function names do not match:" +
                    " name: ${name}," +
                    " gptFunctionCall.functionName: ${gptFunctionCall.functionName}.")
        }
    }
}