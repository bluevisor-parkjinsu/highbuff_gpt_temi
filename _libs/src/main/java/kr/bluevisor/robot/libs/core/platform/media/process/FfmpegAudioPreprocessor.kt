package kr.bluevisor.robot.libs.core.platform.media.process

import android.content.Context
import java.io.File

class FfmpegAudioPreprocessor(context: Context) {
    private val rnnoiseModelFile: File

    init {
        rnnoiseModelFile = File(context.cacheDir, RNNOISE_MODEL__FULL_FILE_NAME)
        copyModelFile(context)
    }

    private fun copyModelFile(context: Context) {
        context.assets.open(RNNOISE_MODEL__FULL_FILE_NAME).use {
            if (rnnoiseModelFile.exists()) return@use
            rnnoiseModelFile.writeBytes(it.readBytes())
        }
    }

    fun getCommandAudioVoiceRecognition(sourceFile: File, targetFile: File) = buildString {
        append("-y -i ${sourceFile.canonicalPath}")
        append(" -af 'arnndn=m=${rnnoiseModelFile.canonicalPath},")
        append(" afftdn=nf=-25, highpass=f=100, lowpass=f=3400'")
        append(" -ar 16000 -ac 1 -c:a aac -b:a 128k ${targetFile.canonicalPath}")
    }

    companion object {
        private const val RNNOISE_MODEL__FULL_FILE_NAME = "rnnoise_std.rnnn"
    }
}