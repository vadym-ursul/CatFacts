package com.sampleapps.catfacts.data.repository

import com.sampleapps.catfacts.data.local.Storage
import com.sampleapps.catfacts.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnboardRepository @Inject constructor(
    private val storage: Storage,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun setOnboardingPassed(passed: Boolean) {
        withContext(defaultDispatcher) {
            storage.saveOnboardingPassed(passed)
        }
    }

    var isOnBoardPassed: Flow<Boolean> = storage.isOnBoardPassed
}