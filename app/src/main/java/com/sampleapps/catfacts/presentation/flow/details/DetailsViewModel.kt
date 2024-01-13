package com.sampleapps.catfacts.presentation.flow.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleapps.catfacts.data.repository.BreedsRepository
import com.sampleapps.catfacts.domain.model.Breed
import com.sampleapps.catfacts.presentation.flow.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val breedsRepository: BreedsRepository,
) : ViewModel() {

    val breed: StateFlow<Breed?> =
        breedsRepository.getBreedByName(savedStateHandle.navArgs<DetailsNavArgs>().breedName)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    fun onFavoriteClick(breed: Breed?, favorite: Boolean) {
        viewModelScope.launch {
            breed?.let {
                breedsRepository.setFavorite(it, favorite)
            }
        }
    }
}