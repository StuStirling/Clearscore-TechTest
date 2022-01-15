package com.stustirling.clearscore.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiAccountInfo(
    @SerializedName("creditReportInfo") val creditReportInfo: ApiCreditReportInfo
)