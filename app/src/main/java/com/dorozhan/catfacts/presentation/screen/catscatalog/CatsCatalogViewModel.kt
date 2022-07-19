package com.dorozhan.catfacts.presentation.screen.catscatalog

import androidx.lifecycle.ViewModel
import com.dorozhan.catfacts.data.repository.BreedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatsCatalogViewModel @Inject constructor(
    breedsRepository: BreedsRepository
) : ViewModel() {

    val breeds = breedsRepository.getBreeds()
}