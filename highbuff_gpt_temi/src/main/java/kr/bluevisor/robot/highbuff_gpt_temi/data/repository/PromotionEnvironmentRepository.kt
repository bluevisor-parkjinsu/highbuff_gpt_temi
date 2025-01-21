package kr.bluevisor.robot.highbuff_gpt_temi.data.repository

import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kr.bluevisor.robot.highbuff_gpt_temi.data.ModelMapper
import kr.bluevisor.robot.highbuff_gpt_temi.data.datasource.ProtoPreferencesDataSource
import kr.bluevisor.robot.highbuff_gpt_temi.data.datasource.setAllCompanyInfo
import kr.bluevisor.robot.highbuff_gpt_temi.data.datasource.setAllUserDefinedQuestionAnswer
import kr.bluevisor.robot.highbuff_gpt_temi.domain.entity.PromotionEnvironment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromotionEnvironmentRepository @Inject constructor(
    private val dataSource: ProtoPreferencesDataSource
) {
    val environmentFlow = dataSource.preferencesFlow.map { protoPreferences ->
        val environment = PromotionEnvironment(
            backgroundImageUriToken = protoPreferences.backgroundImageUriToken,
            personNearbyComment = protoPreferences.personNearbyComment,
            personFarawayComment = protoPreferences.personFarawayComment,
            targetUrl = protoPreferences.targetUrl,
            companyName = protoPreferences.companyName,
            companyTelephoneNumber = protoPreferences.companyTelephoneNumber,
            companyOwnerName = protoPreferences.companyOwnerName,
            userDefinedQuestionAndAnswerList =
            ModelMapper.toPromotionEnvironmentUserDefinedQuestionAndAnswerListEntity(
                protoPreferences.userDefinedQuestionAnswerList
            ),
            companyInfoList =
            ModelMapper.toPromotionEnvironmentCompanyInfoListEntity(
                protoPreferences.companyInfoList
            )
        )

        LLog.v("environment: $environment.")
        environment
    }.flowOn(Dispatchers.IO)

    suspend fun storePromotionEnvironment(environment: PromotionEnvironment) {
        withContext(Dispatchers.IO) {
            dataSource.setPreferences {
                setBackgroundImageUriToken(environment.backgroundImageUriToken)
                setPersonNearbyComment(environment.personNearbyComment)
                setPersonFarawayComment(environment.personFarawayComment)
                setTargetUrl(environment.targetUrl)
                setCompanyName(environment.companyName)
                setCompanyTelephoneNumber(environment.companyTelephoneNumber)
                setCompanyOwnerName(environment.companyOwnerName)
                setAllUserDefinedQuestionAnswer(
                    ModelMapper.toProtoPreferencesUserDefinedQuestionAndAnswerListDataModel(
                        environment.userDefinedQuestionAndAnswerList
                    )
                )
                setAllCompanyInfo(
                    ModelMapper.toProtoPreferencesCompanyInfoListDataModel(
                        environment.companyInfoList
                    )
                )
            }
            LLog.v("environment: $environment.")
        }
    }
}