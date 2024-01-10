package com.sampleapps.catfacts.presentation.flow.details

import androidx.lifecycle.*
import com.sampleapps.catfacts.data.repository.BreedsRepository
import com.sampleapps.catfacts.domain.model.Breed
import com.sampleapps.catfacts.presentation.flow.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
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
                breedsRepository.getBreedByName(savedStateHandle.navArgs<DetailsNavArgs>().breedName)
        }
    }

    fun onFavoriteClick(breed: Breed?, favorite: Boolean) {
        viewModelScope.launch {
            breed?.let {
                breedsRepository.setFavorite(it, favorite)
            }
        }
    }
}