package com.stustirling.clearscore.model.mapper

import com.stustirling.clearscore.model.CreditScore
import com.stustirling.clearscore.model.CreditScoreUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CreditScoreDefaultUiModelMapperTest {

    private val creditScoreDefaultUiModelMapper = CreditScoreDefaultUiModelMapper()

    @Test
    fun `should map credit score to ui model successfully`() {
        val creditScore = CreditScore(
            score = 33,
            maxScoreValue =  78,
            minScoreValue = 1
        )
        assertEquals(
            CreditScoreUiModel(
                score = creditScore.score,
                maxScoreValue = creditScore.maxScoreValue,
                minScoreValue = creditScore.minScoreValue
            ),
            creditScoreDefaultUiModelMapper.toCreditScoreUiModel(creditScore)
        )
    }
}