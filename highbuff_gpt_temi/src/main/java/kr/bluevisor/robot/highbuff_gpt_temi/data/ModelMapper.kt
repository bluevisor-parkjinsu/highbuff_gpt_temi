package kr.bluevisor.robot.highbuff_gpt_temi.data

import kr.bluevisor.robot.highbuff_gpt_temi.CompanyInfo
import kr.bluevisor.robot.highbuff_gpt_temi.UserDefinedQuestionAndAnswer

object ModelMapper {
    fun toPromotionEnvironmentUserDefinedQuestionAndAnswerListEntity(
        qnaDataModelList: List<UserDefinedQuestionAndAnswer>
    ): List<Pair<String, String>> {
        return qnaDataModelList.map { dataModel ->
            dataModel.question to dataModel.answer
        }
    }
}