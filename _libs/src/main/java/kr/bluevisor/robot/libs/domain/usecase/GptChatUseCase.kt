package kr.bluevisor.robot.libs.domain.usecase

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import enn.libs.and.llog.LLog
import kotlinx.coroutines.flow.Flow
import kr.bluevisor.robot.libs.core.platform.media.process.MediaContentResolvers
import kr.bluevisor.robot.libs.data.repository.GptChatRepository
import kr.bluevisor.robot.libs.domain.entity.GptChatCompletion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GptChatUseCase @Inject constructor(private val repository: GptChatRepository) {
    fun sendChatCompletion(chatCompletion: GptChatCompletion) =
        repository.requestToSendChatCompletion(chatCompletion)

    fun sendChatMessages(messages: List<GptChatCompletion.Message>) =
        repository.requestToSendChatMessages(messages)

    fun sendChatMessage(message: GptChatCompletion.Message) =
        repository.requestToSendChatMessage(message)

    fun sendUserChatMessage(message: String) =
        repository.requestToSendChatMessage(
            GptChatCompletion.Message(
                role = GptChatCompletion.ChatRoleType.USER,
                contentList = listOf(Pair(GptChatCompletion.Message.ContentType.TEXT, message))
            )
        )

    private fun sendUserChatMessageWithImages(
        message: String? = null,
        base64EncodedImageBytesTextList: List<Pair<GptChatCompletion.Message.ContentType, String>>
    ): Flow<List<GptChatCompletion.Message>> {
        val chatMessage = newUserChatMessageForImage(message, base64EncodedImageBytesTextList)
        val chatCompletion = GptChatCompletion(
            messageList = listOf(chatMessage),
            model = GptChatCompletion.ModelType.GPT_4O
        )
        val resultFlow = repository.requestToSendChatCompletion(chatCompletion)

        LLog.v("message: $message," +
                " base64EncodedImageBytesTextList.size: ${base64EncodedImageBytesTextList.size}.")
        return resultFlow
    }

    fun sendUserChatMessageWithBitmapImages(
        message: String? = null,
        bitmapList: List<Bitmap>
    ) = sendUserChatMessageWithImages(
        message,
        bitmapList.map(::convertBase64EncodedImageBytesTextContent)
    )

    fun sendUserChatMessageWithImageUris(
        message: String? = null,
        imageContentUriList: List<Uri>,
        contentResolver: ContentResolver
    ) = sendUserChatMessageWithImages(
        message,
        imageContentUriList.map { convertBase64EncodedImageBytesTextContent(it, contentResolver) }
    )

    companion object {
        fun singleText(chatMessageList: List<GptChatCompletion.Message>): String {
            val (_, content) = chatMessageList
                .also {
                    if (it.size == 1) return@also
                    LLog.w("chatMessageList.size is not 1: chatMessageList.size: ${it.size}.")
                }
                .single()
                .contentList
                .also {
                    if (it.size == 1) return@also
                    LLog.w("contentList.size is not 1: contentList.size: ${it.size}.")
                }
                .single()
            return content
        }

        fun newUserChatMessageForImage(
            message: String? = null,
            base64EncodedImageBytesTextList:
                List<Pair<GptChatCompletion.Message.ContentType, String>>
        ) = GptChatCompletion.Message(
            role = GptChatCompletion.ChatRoleType.USER,
            contentList = base64EncodedImageBytesTextList
                .toMutableList()
                .apply {
                    message?.let {
                        add(Pair(GptChatCompletion.Message.ContentType.TEXT, it))
                    }
                }
        )

        fun convertBase64EncodedImageBytesTextContent(bitmap: Bitmap) = Pair(
            GptChatCompletion.Message.ContentType.IMAGE,
            MediaContentResolvers.getBase64EncodedBitmapPngBytesTextFromBitmap(bitmap)
        )

        fun convertBase64EncodedImageBytesTextContent(
            imageContentUri: Uri, contentResolver: ContentResolver
        ) = Pair(
            GptChatCompletion.Message.ContentType.IMAGE,
            MediaContentResolvers.getBase64EncodedBitmapPngBytesTextFromImageContentUri(
                imageContentUri, contentResolver
            )
        )
    }
}