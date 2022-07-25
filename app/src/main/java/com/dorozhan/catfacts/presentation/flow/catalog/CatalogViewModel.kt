package com.dorozhan.catfacts.presentation.flow.catalog

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.dorozhan.catfacts.data.repository.BreedsRepository
import com.dorozhan.catfacts.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository,
) : ViewModel() {

    private val _showSearchBarLiveData = MutableLiveData(false)
    val showSearchBarLiveData: LiveData<Boolean> = _showSearchBarLiveData

    private val _searchTextLiveData = MutableLiveData<String>()
    val searchTextLiveData: LiveData<String> = _searchTextLiveData
    private val _searchActionLiveData = MutableLiveData("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val breedsFlow: Flow<PagingData<Breed>> = _searchActionLiveData.asFlow()
        .flatMapLatest {
            if (it.isEmpty())
                breedsRepository.getBreeds()
            else
                breedsRepository.filterBreeds(it)
        }

    fun searchTextUpdated(text: String) {
        _searchTextLiveData.value = text
        search()
    }

    fun search() {
        val text: String = searchTextLiveData.value ?: ""
        _searchActionLiveData.value = text
    }

    fun onFavoriteClicked(breed: Breed, favorite: Boolean) {
        viewModelScope.launch {
            breedsRepository.setFavorite(breed, favorite)
        }
    }

    fun showSearchBar() {
        _showSearchBarLiveData.value = true
    }

    fun hideSearchBar() {
        _showSearchBarLiveData.value = false
    }
}