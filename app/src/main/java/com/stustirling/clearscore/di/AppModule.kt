package com.stustirling.clearscore.di

import com.stustirling.clearscore.model.mapper.CreditScoreDefaultUiModelMapper
import com.stustirling.clearscore.model.mapper.CreditScoreUiModelMapper
import com.stustirling.clearscore.repository.AccountInfoRepository
import com.stustirling.clearscore.usecase.GetCreditScoreUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Reusable
    fun providesCreditScoreMapper(
        creditScoreUiModelMapper: CreditScoreDefaultUiModelMapper
    ) : CreditScoreUiModelMapper = creditScoreUiModelMapper

    @Provides
    @Reusable
    fun providesGetCreditScoreUseCase(
        accountInfoRepository: AccountInfoRepository,
    ) : GetCreditScoreUseCase = GetCreditScoreUseCase(
        accountInfoRepository
    )
}