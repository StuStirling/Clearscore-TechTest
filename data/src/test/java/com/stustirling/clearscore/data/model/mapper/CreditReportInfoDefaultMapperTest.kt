package com.stustirling.clearscore.data.model.mapper

import com.stustirling.clearscore.data.api.model.ApiCreditReportInfo
import com.stustirling.clearscore.model.CreditReportInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class CreditReportInfoDefaultMapperTest {

    private val creditReportInfoDefaultMapper = CreditReportInfoDefaultMapper()

    @Test
    fun `should map credit report info correctly`() {
        val apiCreditReportInfo = ApiCreditReportInfo(
            score = 20,
            maxScoreValue = 500,
            minScoreValue = 0
        )
        assertEquals(
            CreditReportInfo(
                score = apiCreditReportInfo.score,
                maxScoreValue = apiCreditReportInfo.maxScoreValue,
                minScoreValue = apiCreditReportInfo.minScoreValue
            ),
            creditReportInfoDefaultMapper.toDomainCreditReportInfo(apiCreditReportInfo)
        )
    }

}