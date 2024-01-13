package com.sampleapps.catfacts.presentation.flow.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sampleapps.catfacts.R
import com.sampleapps.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.sampleapps.catfacts.presentation.library.BackAppBar
import com.sampleapps.catfacts.presentation.library.BreedCardItem
import com.sampleapps.catfacts.presentation.library.CatalogAppBar
import com.sampleapps.catfacts.presentation.library.PagingList
import com.sampleapps.catfacts.presentation.library.SearchAppBar
import com.sampleapps.catfacts.presentation.util.rememberLazyGridState
import com.sampleapps.catfacts.presentation.util.setSystemBarsColor

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun CatalogScreen(
    navigator: DestinationsNavigator,
    catalogViewModel: CatalogViewModel = hiltViewModel(),
) {
    val statusBarColor = MaterialTheme.colorScheme.surfaceVariant
    val isSystemInDarkTheme = isSystemInDarkTheme()

    setSystemBarsColor(statusBarColor, statusBarColor.copy(alpha = 0.5f), !isSystemInDarkTheme)

    val showSearchBar = catalogViewModel.showSearchBar.collectAsState(false)
    val showFavorites = catalogViewModel.showFavorites.collectAsState(false)
    val isListLayout = catalogViewModel.isListLayoutFlow.collectAsState()
    val searchTextState = catalogViewModel.searchText.collectAsState(String())

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            when {
                showFavorites.value -> {
                    BackAppBar(
                        text = stringResource(id = R.string.favorites),
                        onBackClick = { catalogViewModel.hideFavorites() })
                }

                showSearchBar.value -> {
                    SearchAppBar(
                        text = searchTextState.value,
                        onBackClick = { catalogViewModel.hideSearchBar() },
                        onTextChange = { catalogViewModel.searchTextUpdated(it) },
                        onSearchClick = { catalogViewModel.search() }
                    )
                }

                else -> {
                    CatalogAppBar(
                        title = stringResource(id = R.string.cat_facts),
                        isListLayout = isListLayout.value,
                        onSearchClick = { catalogViewModel.showSearchBar() },
                        onFavoritesClick = { catalogViewModel.showFavorites() },
                        onLayoutTypeClick = { catalogViewModel.onLayoutTypeClick(it) })
                }
            }
        },
        content = { padding ->
            val items =
                if (showFavorites.value.not()) catalogViewModel.breedsFlow.collectAsLazyPagingItems()
                else catalogViewModel.favoriteBreedsFlow.collectAsLazyPagingItems()
            val listState = items.rememberLazyGridState()
            val swipeState = rememberSwipeRefreshState(
                isRefreshing = items.loadState.refresh is LoadState.Loading && items.itemCount > 0,
            )
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                state = swipeState,
                swipeEnabled = showFavorites.value.not(),
                onRefresh = { items.refresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            ) {
                PagingList(
                    state = listState,
                    items = items,
                    isListLayout = isListLayout.value,
                    itemContent = { item, _ ->
                        BreedCardItem(
                            breed = item,
                            isBreedNameVisible = isListLayout.value,
                            onItemClick = {
                                navigator.navigate(CatDetailsScreenDestination(breedName = it.title))
                            },
                            onFavoriteClick = { breed, checked ->
                                catalogViewModel.onFavoriteClick(breed, checked)
                            }
                        )
                    })
            }
        }
    )
}