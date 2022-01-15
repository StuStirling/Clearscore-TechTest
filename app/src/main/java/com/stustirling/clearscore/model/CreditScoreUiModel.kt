package com.stustirling.clearscore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditScoreUiModel(
    val score: Int,
    val maxScoreValue: Int,
    val minScoreValue: Int
) : Parcelable