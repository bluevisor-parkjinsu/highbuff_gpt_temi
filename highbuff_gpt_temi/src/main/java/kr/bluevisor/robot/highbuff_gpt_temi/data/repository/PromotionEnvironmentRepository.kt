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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromotionEnvironmentRepository @Inject constructor(
    private val dataSource: ProtoPreferencesDataSource
) {
    val environmentFlow = dataSource.preferencesFlow.map { protoPreferences ->
        val environment = UserDefinedQuestionAndAnswer(
            userDefinedQuestionAndAnswerList =
            ModelMapper.toPromotionEnvironmentUserDefinedQuestionAndAnswerListEntity(
                protoPreferences.userDefinedQuestionAnswerList
            )
        )
        LLog.v("environment: $environment.")
        environment
    }.flowOn(Dispatchers.IO)
}