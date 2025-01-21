package kr.bluevisor.robot.libs.core.platform.media.process

import enn.libs.and.llog.LLog
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

open class AudioWavWriter(bufferSize: Int): AutoCloseable {
    private val byteBuffer = ByteArray(bufferSize)
    private val bytesOutputStream = ByteArrayOutputStream()

    val hasStoredBytes get() = bytesOutputStream.size() > 0

    fun storeAudioBytes(sourceBuffer: FloatArray, length: Int) {
        fill(sourceBuffer, byteBuffer, length)
        bytesOutputStream.write(byteBuffer, 0, length * 4)
        LLog.v("length: $length.")
    }

    fun writeAudioFile(wavFile: File, sampleRate: Int, channelCount: Int) {
        val storedBytes = bytesOutputStream.toByteArray()
        writeWavFile(
            wavFile,
            storedBytes,
            sampleRate,
            channelCount,
            BIT_PER_SAMPLE__FLOAT_PCM
        )
        bytesOutputStream.reset()
        LLog.v(
            "wavFile: $wavFile, sampleRate: $sampleRate, channelCount: $channelCount," +
                    " storedBytes.size: ${storedBytes.size}."
        )
    }

    override fun close() {
        bytesOutputStream.close()
        LLog.v()
    }

    companion object {
        val BIT_PER_SAMPLE__FLOAT_PCM = 32

        fun getBufferSizeInSamples(
            channelCount: Int,
            bufferSizeInFrames: Int,
            bitsPerSample: Int,
            bufferElementSize: Int
        ): Int {
            val size = channelCount * bufferSizeInFrames *
                    (bitsPerSample / bufferElementSize.coerceAtMost(bitsPerSample))
            LLog.v(
                "channelCount: $channelCount, $bufferSizeInFrames: $bufferSizeInFrames," +
                        " bitsPerSample: $bitsPerSample, bufferElemenSize: $bufferElementSize," +
                        " size: $size."
            )
            return size
        }

        fun fill(sourceFloatArray: FloatArray, targetByteArray: ByteArray, floatDataLength: Int) {
            val byteBuffer = ByteBuffer.wrap(targetByteArray).order(ByteOrder.LITTLE_ENDIAN)
            for (i in 0 until floatDataLength) {
                byteBuffer.putFloat(i * 4, sourceFloatArray[i])
            }
            LLog.v("floatDataLength: $floatDataLength.")
        }

        fun writeWavFile(
            outputFile: File,
            byteArray: ByteArray,
            sampleRate: Int,
            channelCount: Int,
            bitsPerSample: Int) {
            FileOutputStream(outputFile).use { fileOutputStream ->
                writeWavHeader(
                    fileOutputStream,
                    byteArray.size,
                    sampleRate,
                    channelCount,
                    bitsPerSample
                )
                fileOutputStream.write(byteArray)
            }
            LLog.v(
                "outputFile: $outputFile, sampleRate: $sampleRate, channelCount: $channelCount," +
                        " bitsPerSample: $bitsPerSample."
            )
        }

        fun writeWavHeader(
            outputStream: FileOutputStream,
            totalAudioLength: Int,
            sampleRate: Int,
            channelCount: Int,
            bitsPerSample: Int
        ) {
            val totalDataLen = totalAudioLength + 36
            val byteRate = sampleRate * channelCount * bitsPerSample / 8

            val header = ByteArray(44)
            header[0] = 'R'.code.toByte()
            header[1] = 'I'.code.toByte()
            header[2] = 'F'.code.toByte()
            header[3] = 'F'.code.toByte()
            header[4] = (totalDataLen and 0xff).toByte()
            header[5] = ((totalDataLen shr 8) and 0xff).toByte()
            header[6] = ((totalDataLen shr 16) and 0xff).toByte()
            header[7] = ((totalDataLen shr 24) and 0xff).toByte()
            header[8] = 'W'.code.toByte()
            header[9] = 'A'.code.toByte()
            header[10] = 'V'.code.toByte()
            header[11] = 'E'.code.toByte()
            header[12] = 'f'.code.toByte()
            header[13] = 'm'.code.toByte()
            header[14] = 't'.code.toByte()
            header[15] = ' '.code.toByte()
            header[16] = 16  // Sub-chunk size, 16 for PCM
            header[17] = 0
            header[18] = 0
            header[19] = 0
            header[20] = 1  // Audio format, 1 for PCM
            header[21] = 0
            header[22] = channelCount.toByte()
            header[23] = 0
            header[24] = (sampleRate and 0xff).toByte()
            header[25] = ((sampleRate shr 8) and 0xff).toByte()
            header[26] = ((sampleRate shr 16) and 0xff).toByte()
            header[27] = ((sampleRate shr 24) and 0xff).toByte()
            header[28] = (byteRate and 0xff).toByte()
            header[29] = ((byteRate shr 8) and 0xff).toByte()
            header[30] = ((byteRate shr 16) and 0xff).toByte()
            header[31] = ((byteRate shr 24) and 0xff).toByte()
            header[32] = (channelCount * bitsPerSample / 8).toByte()  // Block align
            header[33] = 0
            header[34] = bitsPerSample.toByte()
            header[35] = 0
            header[36] = 'd'.code.toByte()
            header[37] = 'a'.code.toByte()
            header[38] = 't'.code.toByte()
            header[39] = 'a'.code.toByte()
            header[40] = (totalAudioLength and 0xff).toByte()
            header[41] = ((totalAudioLength shr 8) and 0xff).toByte()
            header[42] = ((totalAudioLength shr 16) and 0xff).toByte()
            header[43] = ((totalAudioLength shr 24) and 0xff).toByte()

            outputStream.write(header, 0, 44)
            LLog.v(
                "totalAudioLength: $totalAudioLength, sampleRate: $sampleRate," +
                        " channelCount: $channelCount, bitsPerSample: $bitsPerSample."
            )
        }
    }
}