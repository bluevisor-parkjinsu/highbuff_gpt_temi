package kr.bluevisor.robot.highbuff_gpt_temi.domain.entity

data class PromotionEnvironment(
    val backgroundImageUriToken: String? = null,
    val personNearbyComment: String? = null,
    val personFarawayComment: String? = null,
    val targetUrl: String? = null,
    val companyName: String? = null,
    val companyTelephoneNumber: String? = null,
    val companyOwnerName: String? = null,
    val userDefinedQuestionAndAnswerList: List<Pair<String, String>> = emptyList(),
    val companyInfoList: List<CompanyInfo> = emptyList()
) {
    data class CompanyInfo(
        val name: String,
        val telephoneNumber: String,
        val ownerName: String
    )
}