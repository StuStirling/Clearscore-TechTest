package com.stustirling.clearscore.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stustirling.clearscore.data.api.AccountInfoService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Reusable
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    @Reusable
    fun provideConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Reusable
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    @Reusable
    fun providesAccountInfoService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): AccountInfoService =
        Retrofit.Builder()
            .baseUrl("https://android-interview.s3.eu-west-2.amazonaws.com/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(AccountInfoService::class.java)

}