package com.example.data.hilt

import com.example.data.api.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Singleton
    @Provides
    fun provideHittaServerConfig(): HittaServerConfig {
        return HittaServer.prod
    }

    @Singleton
    @Provides
    fun provideTestHittaServerConfig(): TestHittaServerConfig {
        return TestHittaServer.prod
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory
            .create(gson)
    }

    @Singleton
    @Provides
    fun provideHittaApi(
        logger: HttpLoggingInterceptor,
        hittaServerConfig: HittaServerConfig,
        converterFactory: GsonConverterFactory
    ): HittaApi {
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(hittaServerConfig.baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
            .create(HittaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTestHittaApi(
        logger: HttpLoggingInterceptor,
        testHittaServerConfig: TestHittaServerConfig,
        converterFactory: GsonConverterFactory
    ): TestHittaApi {
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(testHittaServerConfig.baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()
            .create(TestHittaApi::class.java)
    }
}