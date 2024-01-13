package com.sampleapps.catfacts.data.repository

import com.sampleapps.catfacts.data.local.Storage
import com.sampleapps.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRepository @Inject constructor(
    private val storage: Storage,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun setListLayout(isListLayout: Boolean) {
        withContext(defaultDispatcher) {
            storage.saveListLayout(isListLayout)
        }
    }

    val isListLayout: Flow<Boolean> = storage.isListLayout
}