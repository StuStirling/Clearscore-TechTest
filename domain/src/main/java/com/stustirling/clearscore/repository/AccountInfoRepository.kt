package com.stustirling.clearscore.repository

import com.stustirling.clearscore.model.AccountInfo

interface AccountInfoRepository {
    suspend fun getInfo() : AccountInfo
}