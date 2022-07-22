package com.dorozhan.catfacts.presentation.flow.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.dorozhan.catfacts.data.repository.BreedsRepository
import com.dorozhan.catfacts.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository
) : ViewModel() {

    private val _isFirstSearchDoneLiveData = MutableLiveData<Boolean>()
    val isFirstSearchDoneLiveData: LiveData<Boolean> = _isFirstSearchDoneLiveData
    private val _searchTextLiveData = MutableLiveData<String>()
    val searchTextLiveData: LiveData<String> = _searchTextLiveData
    private val _searchActionLiveData = MutableLiveData<String>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val breedsFlow: Flow<PagingData<Breed>> =
        _searchActionLiveData.asFlow()
            .filter { it.isNotEmpty() }
            .flatMapLatest {
                breedsRepository.filterBreeds(it)
            }

    fun searchTextUpdated(text: String) {
        _searchTextLiveData.value = text
    }

    fun onSearchClicked() {
        _isFirstSearchDoneLiveData.value = true
        val text: String = searchTextLiveData.value ?: ""
        _searchActionLiveData.value = text
    }

    fun onFavoriteClicked(breed: Breed, favorite: Boolean) {
        viewModelScope.launch {
            breedsRepository.setFavorite(breed, favorite)
        }
    }
}