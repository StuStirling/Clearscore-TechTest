package com.stustirling.clearscore.usecase

import com.stustirling.clearscore.model.CreditScore
import com.stustirling.clearscore.repository.AccountInfoRepository

class GetCreditScoreUseCase(
    private val accountInfoRepository: AccountInfoRepository
) {

    suspend operator fun invoke() : CreditScore {
        val info = accountInfoRepository.getInfo()
        return CreditScore(
            score = info.creditReportInfo.score,
            maxScoreValue = info.creditReportInfo.maxScoreValue,
            minScoreValue = info.creditReportInfo.minScoreValue
        )
    }

}