package kr.bluevisor.robot.highbuff_gpt_temi.data

import kr.bluevisor.robot.highbuff_gpt_temi.CompanyInfo
import kr.bluevisor.robot.highbuff_gpt_temi.UserDefinedQuestionAndAnswer
import kr.bluevisor.robot.highbuff_gpt_temi.domain.entity.PromotionEnvironment

object ModelMapper {
    fun toProtoPreferencesUserDefinedQuestionAndAnswerListDataModel(
        qnaEntityList: List<Pair<String, String>>
    ): List<UserDefinedQuestionAndAnswer> {
        return qnaEntityList.map { (question, answer) ->
            UserDefinedQuestionAndAnswer.newBuilder()
                .setQuestion(question)
                .setAnswer(answer)
                .build()
        }
    }

    fun toProtoPreferencesCompanyInfoListDataModel(
        infoEntityList: List<PromotionEnvironment.CompanyInfo>
    ): List<CompanyInfo> {
        return infoEntityList.map { entity ->
            CompanyInfo.newBuilder()
                .setName(entity.name)
                .setTelephoneNumber(entity.telephoneNumber)
                .setOwnerName(entity.ownerName)
                .build()
        }
    }

    fun toPromotionEnvironmentUserDefinedQuestionAndAnswerListEntity(
        qnaDataModelList: List<UserDefinedQuestionAndAnswer>
    ): List<Pair<String, String>> {
        return qnaDataModelList.map { dataModel ->
            dataModel.question to dataModel.answer
        }
    }

    fun toPromotionEnvironmentCompanyInfoListEntity(
        infoDataModelList: List<CompanyInfo>
    ): List<PromotionEnvironment.CompanyInfo> {
        return infoDataModelList.map { dataModel ->
            PromotionEnvironment.CompanyInfo(
                name = dataModel.name,
                telephoneNumber = dataModel.telephoneNumber,
                ownerName = dataModel.ownerName
            )
        }
    }
}