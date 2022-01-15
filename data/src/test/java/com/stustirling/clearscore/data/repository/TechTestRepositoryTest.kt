package com.stustirling.clearscore.data.repository

import com.stustirling.clearscore.core.coroutines.DispatcherProvider
import com.stustirling.clearscore.data.api.AccountInfoService
import com.stustirling.clearscore.data.api.model.ApiAccountInfo
import com.stustirling.clearscore.data.api.model.ApiCreditReportInfo
import com.stustirling.clearscore.data.model.mapper.AccountInfoMapper
import com.stustirling.clearscore.model.AccountInfo
import com.stustirling.clearscore.model.CreditReportInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TechTestRepositoryTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val dispatcherProvider = object : DispatcherProvider() {
        override fun providesIO(): CoroutineDispatcher = testDispatcher
    }

    private val accountInfoService: AccountInfoService = mock()
    private val accountInfoMapper: AccountInfoMapper = mock()

    private val repo = TechTestRepository(
        dispatcherProvider = dispatcherProvider,
        accountInfoService = accountInfoService,
        accountInfoMapper = accountInfoMapper
    )


    @Test
    fun `should retrieve account info from service and use mapper`() {
        val apiAccountInfo = ApiAccountInfo(
            ApiCreditReportInfo(score = 4, maxScoreValue = 1000, minScoreValue = 0)
        )
        runBlocking {
            whenever(accountInfoService.getInfo())
                .doSuspendableAnswer { withContext(testDispatcher) { apiAccountInfo } }
            val domainAccountInfo =
                AccountInfo(CreditReportInfo(score = 8, maxScoreValue = 2000, minScoreValue = 1))
            whenever(accountInfoMapper.toDomainAccountInfo(apiAccountInfo))
                .thenReturn(domainAccountInfo)

            assertEquals(
                domainAccountInfo,
                repo.getInfo()
            )
        }
    }

}