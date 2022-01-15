package com.stustirling.clearscore.data.repository

import com.stustirling.clearscore.core.coroutines.DispatcherProvider
import com.stustirling.clearscore.data.api.AccountInfoService
import com.stustirling.clearscore.data.model.mapper.AccountInfoMapper
import com.stustirling.clearscore.model.AccountInfo
import com.stustirling.clearscore.repository.AccountInfoRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TechTestRepository @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val accountInfoService: AccountInfoService,
    private val accountInfoMapper: AccountInfoMapper
) : AccountInfoRepository {

    override suspend fun getInfo(): AccountInfo =
        withContext(dispatcherProvider.providesIO()) {
            val apiAccountInfo = accountInfoService.getInfo()
            accountInfoMapper.toDomainAccountInfo(apiAccountInfo)
        }

}