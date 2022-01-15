package com.stustirling.clearscore.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiCreditReportInfo(
    @SerializedName("score") val score: Int,
    @SerializedName("maxScoreValue") val maxScoreValue: Int,
    @SerializedName("minScoreValue") val minScoreValue: Int
)