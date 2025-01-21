package kr.bluevisor.robot.libs.data.model

data class GptChatCompletionV1ResponseRemoteDataModel(
    val id: String,
    val choices: List<Choice>,
    val created: Int,
    val model: String,
    val system_fingerprint: String,
    val `object`: String,
    val usage: Usage,
) {
    data class Choice(
        val finish_reason: String,
        val index: Int,
        val message: Message,
        val logprobs: Logprobs?,
    ) {
        data class Message(
            val content: String?,
            val refusal: String?,
            val tool_calls: List<GptToolFunctionSubResponseRemoteDataModel> = emptyList(),
            val role: String
        )

        data class Logprobs(
            val content: List<Content>?,
        ) {
            data class Content(
                val token: String,
                val logprob: Int,
                val bytes: List<Byte>?,
                val top_logprobs: List<TopLogprobs>,
            ) {
                data class TopLogprobs(
                    val token: String,
                    val logprob: Int,
                    val bytes: List<Byte>?,
                ) {

                }
            }
        }
    }

    data class Usage(
        val completion_tokens: Int,
        val prompt_tokens: Int,
        val total_tokens: Int,
    )
}