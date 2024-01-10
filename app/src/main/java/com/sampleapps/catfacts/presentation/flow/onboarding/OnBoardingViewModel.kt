package com.sampleapps.catfacts.presentation.flow.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapps.catfacts.data.repository.OnboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onboardRepository: OnboardRepository,
) : ViewModel() {

    fun finishOnboard() {
        viewModelScope.launch {
            onboardRepository.setOnboardingPassed(true)
        }
    }
}