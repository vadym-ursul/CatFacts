package com.dorozhan.catfacts.presentation.flow.favorites

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.dorozhan.catfacts.data.repository.BreedsRepository
import com.dorozhan.catfacts.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository,
) : ViewModel() {

    val favoriteBreedsFlow: Flow<PagingData<Breed>> = breedsRepository.getFavoriteBreeds()
}