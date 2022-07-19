package com.dorozhan.catfacts.presentation.screen.catscatalog

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.dorozhan.catfacts.data.repository.BreedsRepository
import com.dorozhan.catfacts.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CatsCatalogViewModel @Inject constructor(
    breedsRepository: BreedsRepository
) : ViewModel() {

    val breedsFlow: Flow<PagingData<Breed>> = breedsRepository.getBreeds()
}