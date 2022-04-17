package com.dorozhan.catfacts.di

import android.util.Log
import com.dorozhan.catfacts.data.network.CATFACT_NINJA_URL
import com.dorozhan.catfacts.data.network.TIMEOUT
import com.dorozhan.catfacts.data.network.ktor.ApiImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import com.dorozhan.catfacts.data.network.ktor.Api as KtorApi
import com.dorozhan.catfacts.data.network.retrofit.Api as RetrofitApi

val ktorModule = module {
    single { httpClient() }
    single { ktorApiService(get()) }
}

val retrofitModule = module {
    single { headerInterceptor() }
    single { okhttpClient(get()) }
    single { converterFactory() }
    single { retrofit(get(), get()) }
    single { apiService(get()) }
}

// Ktor
fun ktorApiService(
    httpClient: HttpClient
): KtorApi {
    return ApiImpl(httpClient)
}

fun httpClient(): HttpClient {
    return HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        Logging {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
        }
        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}

// Retrofit
fun apiService(
    retrofit: Retrofit
): RetrofitApi =
    retrofit.create(RetrofitApi::class.java)

fun retrofit(
    okHttpClient: OkHttpClient,
    factory: Converter.Factory
): Retrofit =
    Retrofit.Builder()
        .baseUrl(CATFACT_NINJA_URL)
        .client(okHttpClient)
        .addConverterFactory(factory)
        .build()

@OptIn(ExperimentalSerializationApi::class)
fun converterFactory(): Converter.Factory {
    val contentType = "application/json".toMediaType()
    return Json.asConverterFactory(contentType)
}

fun okhttpClient(
    headerInterceptor: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        .build()

fun headerInterceptor(): Interceptor =
    Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url.newBuilder()
//            .addQueryParameter()
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }