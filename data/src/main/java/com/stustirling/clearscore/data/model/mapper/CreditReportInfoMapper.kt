package com.stustirling.clearscore.data.model.mapper

import com.stustirling.clearscore.data.api.model.ApiCreditReportInfo
import com.stustirling.clearscore.model.CreditReportInfo

interface CreditReportInfoMapper {
    fun toDomainCreditReportInfo(apiCreditReportInfo: ApiCreditReportInfo): CreditReportInfo
}

class CreditReportInfoDefaultMapper : CreditReportInfoMapper {
    override fun toDomainCreditReportInfo(apiCreditReportInfo: ApiCreditReportInfo) : CreditReportInfo =
        CreditReportInfo(
            score = apiCreditReportInfo.score,
            maxScoreValue = apiCreditReportInfo.maxScoreValue,
            minScoreValue = apiCreditReportInfo.minScoreValue
        )
}