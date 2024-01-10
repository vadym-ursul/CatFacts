package com.sampleapps.catfacts.presentation.flow.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapps.catfacts.data.repository.OnboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    onboardRepository: OnboardRepository,
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading
    val onboardPassedFlow: StateFlow<Boolean?> =
        onboardRepository.isOnBoardPassed
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}