package com.dorozhan.catfacts.presentation

import androidx.lifecycle.ViewModel
import com.dorozhan.catfacts.data.local.Storage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storage: Storage,
) : ViewModel() {

    var isFirstTimeLaunch: Boolean
        get() = storage.isFirstTimeLaunch
        set(passed) {
            storage.isFirstTimeLaunch = passed
        }
}