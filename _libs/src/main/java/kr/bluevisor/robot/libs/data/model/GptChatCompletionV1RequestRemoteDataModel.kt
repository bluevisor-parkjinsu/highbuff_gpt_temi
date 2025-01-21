package kr.bluevisor.robot.libs.data.model

data class GptChatCompletionV1RequestRemoteDataModel(
    // messages can contain SystemMessage, UserMessage, AssistantMessage, ToolMessage.
    val messages: List<Any>,
    val model: String,
    val frequency_penalty: Int? = null,
    val logit_bias: Map<String, String>? = null,
    val logprobs: Boolean? = null,
    val top_logprobs: Int? = null,
    val max_tokens: Int? = null,
    val n: Int? = null,
    val presence_penalty: Int? = null,
    val response_format: ResponseFormat? = null,
    val seed: Int? = null,
    val stop: Any? = null,  // Can be String, String array.
    val stream: Boolean? = null,
    val stream_options: StreamOptions? = null,
    val temperature: Double? = null,
    val top_p: Int? = null,
    val tools: List<GptToolFunctionSubRequestRemoteDataModel>? = null,
    val tool_choice: Any? = null,   // Can be String or ToolChoice.
    val user: String? = null,
) {
    interface Messageable {
        val content: Any?
        val role: String

        companion object {
            const val FIELD_VALUE__ROLE__SYSTEM = "system"
            const val FIELD_VALUE__ROLE__USER = "user"
            const val FIELD_VALUE__ROLE__ASSISTANT = "assistant"
            const val FIELD_VALUE__ROLE__TOOL = "tool"
        }
    }

    data class SystemMessage(
        override val content: Any,
        override val role: String = Messageable.FIELD_VALUE__ROLE__SYSTEM,
        val name: String? = null,
    ) : Messageable

    data class UserMessage(
        override val content: Any,
        override val role: String = Messageable.FIELD_VALUE__ROLE__USER,
        val name: String? = null    // can be String or some Content array.
    ) : Messageable {
        data class TextContentPart(
            val type: String = FIELD_VALUE__TYPE__TEXT_CONTENT_PART,
            val text: String,
        )

        data class ImageContentPart(
            val type: String = FIELD_VALUE__TYPE__IMAGE_CONTENT_PART,
            val image_url: ImageUrl,
        ) {
            data class ImageUrl(
                val url: String,
                val detail: String? = null
            )
        }

        companion object {
            const val FIELD_VALUE__TYPE__TEXT_CONTENT_PART = "text"
            const val FIELD_VALUE__TYPE__IMAGE_CONTENT_PART = "image_url"
        }
    }

    data class AssistantMessage(
        override val content: Any? = null,
        val refusal: String? = null,
        override val role: String = Messageable.FIELD_VALUE__ROLE__ASSISTANT,
        val name: String? = null,
        val tool_calls: List<ToolCalls>? = null,
    ) : Messageable {
        data class ToolCalls(
            val id: String,
            val type: String = FIELD_VALUE__TYPE__TYPE__FUNCTION,
            val function: Function,
        ) {
            data class Function(
                val name: String,
                val arguments: String,
            )

            companion object {
                const val FIELD_VALUE__TYPE__TYPE__FUNCTION = "function"
            }
        }
    }

    data class ToolMessage(
        override val role: String = Messageable.FIELD_VALUE__ROLE__TOOL,
        override val content: Any,
        val tool_call_id: String,
    ) : Messageable

    data class ResponseFormat(
        val type: String? = null
    )

    data class StreamOptions(
        val include_usage: Boolean? = null,
    )

    data class ToolChoice(
        val type: String,
        val function: Function,
    ) {
        data class Function(
            val name: String
        )
    }

    companion object {
        const val FIELD_VALUE__MODEL__GPT_4O = "gpt-4o"
        const val FIELD_VALUE__MODEL__GPT_4O_MINI = "gpt-4o-mini"
        const val FIELD_VALUE__TOOL_CHOICE__NONE = "none"
        const val FIELD_VALUE__TOOL_CHOICE__AUTO = "auto"
        const val FIELD_VALUE__TOOL_CHOICE__REQUIRED = "required"
    }
}