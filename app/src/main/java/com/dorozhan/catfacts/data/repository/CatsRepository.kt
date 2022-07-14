package com.dorozhan.catfacts.data.repository

import com.dorozhan.catfacts.data.remote.retrofit.Api
import com.dorozhan.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatsRepository @Inject constructor(
    private val api: Api,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun getBreeds(pageNumber: Int) =
        withContext(defaultDispatcher) { api.getBreeds(pageNumber) }
}