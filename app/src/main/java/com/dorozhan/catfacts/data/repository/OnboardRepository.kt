package com.dorozhan.catfacts.data.repository

import com.dorozhan.catfacts.data.local.Storage
import javax.inject.Inject

class OnboardRepository @Inject constructor(
    private val storage: Storage
) {

    var isOnBoardPassed: Boolean
        get() = storage.isOnBoardPassed
        set(passed) {
            storage.isOnBoardPassed = passed
        }
}