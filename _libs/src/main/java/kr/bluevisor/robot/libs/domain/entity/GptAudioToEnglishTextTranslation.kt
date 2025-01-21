package kr.bluevisor.robot.libs.domain.entity

import enn.libs.and.llog.LLog
import java.io.File

data class GptAudioToEnglishTextTranslation(
    val audioFile: File,
    val text: String? = null,
) {
    init {
        if (text != null && text.isBlank()) {
            LLog.i("text is empty or has only whitespaces.")
        }
    }

    val isAudioOnly = text == null

    val existAudioFile
        get() = audioFile.exists()
}