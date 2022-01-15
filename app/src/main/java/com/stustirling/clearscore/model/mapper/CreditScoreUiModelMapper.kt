package com.stustirling.clearscore.model.mapper

import com.stustirling.clearscore.model.CreditScore
import com.stustirling.clearscore.model.CreditScoreUiModel
import javax.inject.Inject

interface CreditScoreUiModelMapper {
    fun toCreditScoreUiModel(creditScore: CreditScore) : CreditScoreUiModel
}

class CreditScoreDefaultUiModelMapper @Inject constructor() : CreditScoreUiModelMapper {
    override fun toCreditScoreUiModel(creditScore: CreditScore): CreditScoreUiModel =
        CreditScoreUiModel(
            score = creditScore.score,
            maxScoreValue = creditScore.maxScoreValue,
            minScoreValue = creditScore.minScoreValue
        )
}