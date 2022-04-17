package com.dorozhan.catfacts.data.repository

import com.dorozhan.catfacts.data.network.retrofit.Api
import com.dorozhan.catfacts.data.network.ktor.Api as KtorApi

class CatsRepository(
    private val api: Api,
    private val ktorApi: KtorApi,
) {
    suspend fun getBreeds(pageNumber: Int) = ktorApi.getBreeds(pageNumber)
}