package com.dorozhan.catfacts.presentation.flow.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dorozhan.catfacts.data.repository.OnboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    onboardRepository: OnboardRepository,
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _onboardPassedLiveData = MutableLiveData<Boolean>()
    val onboardPassedLiveData: LiveData<Boolean> = _onboardPassedLiveData

    init {
        _onboardPassedLiveData.value = onboardRepository.isOnBoardPassed
        _isLoading.value = false
    }
}