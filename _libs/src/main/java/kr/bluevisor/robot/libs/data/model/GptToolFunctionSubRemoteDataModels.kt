package kr.bluevisor.robot.libs.data.model

data class GptToolFunctionSubRequestRemoteDataModel(
    val type: String = "function",
    val function: Function
) {
    data class Function(
        val name: String,
        val description: String,
        val parameters: Parameters
    ) {
        data class Parameters(
            val type: String = "object",
            val properties: Map<String, Property>,
            val required: List<String>
        ) {
            data class Property(
                val type: String = "string",
                val enum: List<Any>? = null,
                val description: String
            )
        }
    }
}

data class GptToolFunctionSubResponseRemoteDataModel(
    val id: String,
    val type: String = "function",
    val function: Function
) {
    data class Function(
        val arguments: String,
        val name: String
    )
}