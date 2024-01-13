package com.sampleapps.catfacts.presentation.flow.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sampleapps.catfacts.data.repository.BreedsRepository
import com.sampleapps.catfacts.data.repository.CatalogRepository
import com.sampleapps.catfacts.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val breedsRepository: BreedsRepository,
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _showSearchBar = MutableStateFlow(false)
    val showSearchBar: StateFlow<Boolean> = _showSearchBar.asStateFlow()
    private val _showFavorites = MutableStateFlow(false)
    val showFavorites: StateFlow<Boolean> = _showFavorites.asStateFlow()

    private val _searchText = MutableStateFlow(String())
    val searchText: StateFlow<String> = _searchText
    private val _searchAction = MutableStateFlow(String())

    val isListLayoutFlow: StateFlow<Boolean> =
        catalogRepository.isListLayout
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = true
            )

    @OptIn(ExperimentalCoroutinesApi::class)
    val breedsFlow: Flow<PagingData<Breed>> = _searchAction
        .flatMapLatest {
            if (it.isEmpty())
                breedsRepository.getBreeds()
            else
                breedsRepository.filterBreeds(it)
        }

    val favoriteBreedsFlow: Flow<PagingData<Breed>> = breedsRepository.getFavoriteBreeds()

    fun searchTextUpdated(text: String) {
        _searchText.value = text
        search()
    }

    fun search() {
        val text: String = searchText.value
        _searchAction.value = text
    }

    fun onFavoriteClick(breed: Breed, favorite: Boolean) {
        viewModelScope.launch {
            breedsRepository.setFavorite(breed, favorite)
        }
    }

    fun onLayoutTypeClick(isListLayout: Boolean) {
        viewModelScope.launch {
            catalogRepository.setListLayout(isListLayout)
        }
    }

    fun showSearchBar() {
        _showSearchBar.value = true
    }

    fun hideSearchBar() {
        _showSearchBar.value = false
    }

    fun showFavorites() {
        _showFavorites.value = true
    }

    fun hideFavorites() {
        _showFavorites.value = false
    }
}