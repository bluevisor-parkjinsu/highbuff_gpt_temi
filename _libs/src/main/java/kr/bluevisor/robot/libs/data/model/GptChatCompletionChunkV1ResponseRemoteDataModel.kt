package kr.bluevisor.robot.libs.data.model

data class GptChatCompletionChunkV1ResponseRemoteDataModel(
    val id: String,
    val choices: List<Choice>,
    val logprobs: Logprobs? = null,
    val created: Int,
    val model: String,
    val system_fingerprint: String,
    val `object`: String,
    val usage: Usage,
) {
    data class Choice(
        val delta: Delta,
        val finish_reason: String? = null,
        val index: Int,
    ) {
        data class Delta(
            val content: String? = null,
            val tool_calls: List<ToolCalls>,
            val role: String,
        ) {
            data class ToolCalls(
                val index: Int,
                val id: String,
                val type: String,
                val function: Function,
            ) {
                data class Function(
                    val name: String,
                    val arguments: String,
                )
            }
        }
    }

    data class Logprobs(
        val content: List<Content>,
    ) {
        data class Content(
            val token: String,
            val logprobs: Double,
            val bytes: List<Byte>,
            val top_logprobs: List<TopLogprobs>,
        ) {
            data class TopLogprobs(
                val token: String,
                val logprob: Double,
                val bytes: List<Byte>,
            )
        }
    }

    data class Usage(
        val completion_tokens: Int,
        val prompt_tokens: Int,
        val total_tokens: Int,
    )
}