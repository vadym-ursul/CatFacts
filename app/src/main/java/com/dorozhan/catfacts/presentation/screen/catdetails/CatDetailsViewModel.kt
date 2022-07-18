package com.dorozhan.catfacts.presentation.screen.catdetails

import androidx.lifecycle.*
import com.dorozhan.catfacts.data.repository.BreedsRepository
import com.dorozhan.catfacts.domain.model.Breed
import com.dorozhan.catfacts.presentation.screen.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val breedsRepository: BreedsRepository,
) : ViewModel() {

    private val _breedLiveData = MutableLiveData<Breed>()
    val breedLiveData: LiveData<Breed> = _breedLiveData

    init {
        getBreed()
    }

    private fun getBreed() {
        viewModelScope.launch {
            _breedLiveData.value =
                breedsRepository.getBreedByName(savedStateHandle.navArgs<CatDetailsNavArgs>().breedName)
        }
    }
}