package com.dorozhan.catfacts.presentation.screen.search

import androidx.lifecycle.ViewModel
import com.dorozhan.catfacts.data.repository.BreedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    breedsRepository: BreedsRepository
) : ViewModel() {

}