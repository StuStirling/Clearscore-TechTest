package com.stustirling.clearscore.data.api

import com.stustirling.clearscore.data.api.model.ApiAccountInfo
import retrofit2.http.GET

interface AccountInfoService {

    @GET("endpoint.json")
    suspend fun getInfo() : ApiAccountInfo

}