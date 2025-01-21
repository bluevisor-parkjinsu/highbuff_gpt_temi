package kr.bluevisor.robot.libs.data.model

data class GptAudioTranscriptionVerboseV1ResponseRemoteDataModel(
    val language: String,
    val duration: String,
    val text: String,
    val words: List<Word>,
    val segments: List<Segment>,
) {
    data class Word(
        val word: String,
        val start: Double,
        val end: Double,
    )

    data class Segment(
        val id: Int,
        val seek: Int,
        val start: Double,
        val end: Double,
        val text: String,
        val tokens: List<String>,
        val temperature: Double,
        val avg_logprob: Double,
        val compression_ratio: Double,
        val no_speech_prob: Double,
    )
}