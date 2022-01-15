package com.stustirling.clearscore.data.model.mapper

import com.stustirling.clearscore.data.api.model.ApiAccountInfo
import com.stustirling.clearscore.data.api.model.ApiCreditReportInfo
import com.stustirling.clearscore.model.AccountInfo
import com.stustirling.clearscore.model.CreditReportInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AccountInfoDefaultMapperTest {

    private val creditReportInfoMapper : CreditReportInfoMapper = mock()

    private val accountInfoDefaultMapper = AccountInfoDefaultMapper(creditReportInfoMapper)

    @Test
    fun `should map account info correctly`() {
        val mockedCreditReportInfo = CreditReportInfo(
            score = 40,
            maxScoreValue = 800,
            minScoreValue = 0
        )
        val apiCreditReportInfo = ApiCreditReportInfo(
            score = 20,
            maxScoreValue = 400,
            minScoreValue = 20
        )
        whenever(creditReportInfoMapper.toDomainCreditReportInfo(apiCreditReportInfo))
            .thenReturn(mockedCreditReportInfo)
        val apiAccountInfo = ApiAccountInfo(
            creditReportInfo = apiCreditReportInfo
        )
        assertEquals(
            AccountInfo(creditReportInfo = mockedCreditReportInfo),
            accountInfoDefaultMapper.toDomainAccountInfo(apiAccountInfo)
        )
    }

}