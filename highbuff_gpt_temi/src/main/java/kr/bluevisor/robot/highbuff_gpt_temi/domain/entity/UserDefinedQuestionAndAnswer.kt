package kr.bluevisor.robot.highbuff_gpt_temi.domain.entity

data class UserDefinedQuestionAndAnswer(
    val userDefinedQuestionAndAnswerList: List<Pair<String, String>> = emptyList(),
)