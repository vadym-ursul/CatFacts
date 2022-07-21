package com.dorozhan.catfacts.presentation.flow.catscatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dorozhan.catfacts.data.repository.BreedsRepository
import com.dorozhan.catfacts.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository
) : ViewModel() {

    val breedsFlow: Flow<PagingData<Breed>> = breedsRepository.getBreeds()

    fun onFavoriteClicked(breed: Breed, favorite: Boolean) {
        viewModelScope.launch {
            breedsRepository.setFavorite(breed, favorite)
        }
    }
}