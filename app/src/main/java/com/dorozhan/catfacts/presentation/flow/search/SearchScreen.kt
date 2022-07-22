package com.dorozhan.catfacts.presentation.flow.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.dorozhan.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.dorozhan.catfacts.presentation.library.BreedsLazyColumn
import com.dorozhan.catfacts.presentation.library.SearchAppBar
import com.dorozhan.catfacts.presentation.util.rememberLazyListState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val searchFirstState = searchViewModel.isFirstSearchDoneLiveData.observeAsState()
    Scaffold(
        topBar = {
            SearchAppBar(
                text = searchViewModel.searchTextLiveData.observeAsState("").value,
                onBackClick = { navigator.navigateUp() },
                onTextChange = {
                    searchViewModel.searchTextUpdated(it)
                },
                onSearchClick = {
                    searchViewModel.onSearchClicked()
                }
            )
        },
        content = { padding ->
            val items = searchViewModel.breedsFlow.collectAsLazyPagingItems()
            val listState = items.rememberLazyListState()

            if (searchFirstState.value != null) {
                BreedsLazyColumn(
                    modifier = Modifier.padding(padding),
                    state = listState,
                    items = items,
                    onBreedItemClick = {
                        navigator.navigate(CatDetailsScreenDestination(breedName = it.title))
                    },
                    onFavoriteClick = { breed, checked ->
                        searchViewModel.onFavoriteClicked(breed, checked)
                    }
                )
            }
        }
    )
}