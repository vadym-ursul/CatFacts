package com.dorozhan.catfacts.di

import com.dorozhan.catfacts.data.remote.CATFACT_NINJA_URL
import com.dorozhan.catfacts.data.remote.TIMEOUT
import com.dorozhan.catfacts.data.remote.retrofit.Api
import com.dorozhan.catfacts.data.remote.retrofit.adapter.ApiResponseAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return CATFACT_NINJA_URL
    }

    @Provides
    fun provideOkHttpClient(@BaseUrl baseUrl: String): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            addInterceptor { chain ->
                val request = chain.request()
                val newUrl = request.url.newBuilder()
//            .addQueryParameter()
                    .build()

                val newRequest = request.newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
        }.build()
    }

    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun provideConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json.asConverterFactory(contentType)
    }

    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = ApiResponseAdapterFactory()

    @Provides
    @Singleton
    fun provideApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): Api {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(Api::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl
}