package com.stustirling.usecase

import com.stustirling.clearscore.model.AccountInfo
import com.stustirling.clearscore.model.CreditReportInfo
import com.stustirling.clearscore.model.CreditScore
import com.stustirling.clearscore.repository.AccountInfoRepository
import com.stustirling.clearscore.usecase.GetCreditScoreUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCreditScoreUseCaseTest {

    private val accountInfoRepository: AccountInfoRepository = mock()

    private val getCreditScore = GetCreditScoreUseCase(accountInfoRepository)

    @Test
    fun `should retrieve account info and map to credit score`() {
        runBlocking {
            val accountInfo = AccountInfo(
                CreditReportInfo(
                    score = 100,
                    maxScoreValue = 100,
                    minScoreValue = 0
                )
            )
            whenever(accountInfoRepository.getInfo())
                .thenReturn(accountInfo)
            assertEquals(
                CreditScore(
                    score = accountInfo.creditReportInfo.score,
                    maxScoreValue = accountInfo.creditReportInfo.maxScoreValue,
                    minScoreValue = accountInfo.creditReportInfo.minScoreValue
                ),
                getCreditScore()
            )

        }
    }

}