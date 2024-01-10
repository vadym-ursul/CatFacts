package com.dorozhan.catfacts.data.repository

import com.dorozhan.catfacts.data.local.Storage
import com.dorozhan.catfacts.di.IoDispatcher
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