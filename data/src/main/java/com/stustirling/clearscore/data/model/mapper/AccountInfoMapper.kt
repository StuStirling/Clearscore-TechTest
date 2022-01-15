package com.stustirling.clearscore.data.model.mapper

import com.stustirling.clearscore.data.api.model.ApiAccountInfo
import com.stustirling.clearscore.model.AccountInfo

interface AccountInfoMapper {
    fun toDomainAccountInfo(apiAccountInfo: ApiAccountInfo): AccountInfo
}

class AccountInfoDefaultMapper(private val creditReportInfoMapper: CreditReportInfoMapper) :
    AccountInfoMapper {
    override fun toDomainAccountInfo(apiAccountInfo: ApiAccountInfo): AccountInfo =
        AccountInfo(
            creditReportInfo = creditReportInfoMapper
                .toDomainCreditReportInfo(apiAccountInfo.creditReportInfo)
        )
}