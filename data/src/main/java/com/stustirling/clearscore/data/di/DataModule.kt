package com.stustirling.clearscore.data.di

import com.stustirling.clearscore.data.model.mapper.AccountInfoDefaultMapper
import com.stustirling.clearscore.data.model.mapper.AccountInfoMapper
import com.stustirling.clearscore.data.model.mapper.CreditReportInfoDefaultMapper
import com.stustirling.clearscore.data.model.mapper.CreditReportInfoMapper
import com.stustirling.clearscore.data.repository.TechTestRepository
import com.stustirling.clearscore.repository.AccountInfoRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Reusable
    fun providesCreditReportInfoMapper() : CreditReportInfoMapper =
        CreditReportInfoDefaultMapper()

    @Provides
    @Reusable
    fun providesAccountInfoMapper(
        creditReportInfoMapper: CreditReportInfoMapper
    ) : AccountInfoMapper = AccountInfoDefaultMapper(creditReportInfoMapper)

    @Provides
    @Reusable
    fun providesAccountInfoRepository(
        techTestRepository: TechTestRepository
    ) : AccountInfoRepository = techTestRepository

}