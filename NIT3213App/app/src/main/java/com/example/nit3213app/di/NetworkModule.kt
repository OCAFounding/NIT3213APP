package com.example.nit3213app.di

import com.example.nit3213app.data.remote.ApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Hilt module for network-related dependencies
 * 
 * This module provides all the networking components needed for API calls:
 * - OkHttpClient for HTTP requests
 * - Moshi for JSON serialization
 * - Retrofit for API interface implementation
 * - ApiService for NIT3213 API endpoints
 * 
 * Key Concepts:
 * - Hilt modules provide dependencies
 * - Singleton scope for app-wide instances
 * - Network configuration and logging
 * - Base URL configuration
 * 
 * Assignment Requirements:
 * - Provides Retrofit and OkHttp dependencies
 * - Configures API base URL
 * - Enables network logging for debugging
 * - Uses Hilt for dependency injection
 */

// Base URL for NIT3213 API
private const val BASE_URL = "https://nit3213api.onrender.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides OkHttpClient with logging and timeout configuration
     * 
     * @return Configured OkHttpClient instance
     */
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    // Log HTTP requests and responses for debugging
                    // In production, use Level.BASIC or Level.NONE
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    /**
     * Provides Moshi instance for JSON serialization
     * 
     * @return Moshi instance with default configuration
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    /**
     * Provides Retrofit instance with base URL and converters
     * 
     * @param client OkHttpClient for HTTP requests
     * @param moshi Moshi instance for JSON conversion
     * @return Configured Retrofit instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

    /**
     * Provides ApiService implementation
     * 
     * @param retrofit Retrofit instance
     * @return ApiService interface implementation
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
