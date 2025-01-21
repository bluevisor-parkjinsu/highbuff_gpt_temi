package kr.bluevisor.robot.libs.domain.entity

data class GptToolFunction(
    val name: String,
    val description: String,
    val parameterList: List<Parameter> = emptyList(),
) {
    data class Parameter(
        val name: String,
        val type: String = "string",
        val argumentRange: List<Any> = emptyList(),
        val description: String,
        val required: Boolean,
    )

    data class Call(
        val id: String,
        val functionName: String,
        val parameterArgumentMap: Map<String, Any>
    )

    data class Callback(
        val id: String,
        val returnValue: String
    )
}