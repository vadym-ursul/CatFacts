package com.dorozhan.catfacts.presentation.flow.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.dorozhan.catfacts.presentation.flow.destinations.FavoritesScreenDestination
import com.dorozhan.catfacts.presentation.library.BreedCardItem
import com.dorozhan.catfacts.presentation.library.CatalogAppBar
import com.dorozhan.catfacts.presentation.library.PagingList
import com.dorozhan.catfacts.presentation.library.SearchAppBar
import com.dorozhan.catfacts.presentation.util.rememberLazyListState
import com.dorozhan.catfacts.presentation.util.setSystemBarsColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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

    val showSearchBar = catalogViewModel.showSearchBarLiveData.observeAsState(false).value
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            if (!showSearchBar) {
                CatalogAppBar(title = stringResource(id = R.string.cat_facts),
                    onSearchClick = { catalogViewModel.showSearchBar() },
                    onFavoritesClick = { navigator.navigate(FavoritesScreenDestination) })
            } else {
                SearchAppBar(
                    text = catalogViewModel.searchTextLiveData.observeAsState("").value,
                    onBackClick = { catalogViewModel.hideSearchBar() },
                    onTextChange = {
                        catalogViewModel.searchTextUpdated(it)
                    },
                    onSearchClick = {
                        catalogViewModel.search()
                    }
                )
            }
        },
        content = { padding ->
            val items = catalogViewModel.breedsFlow.collectAsLazyPagingItems()
            val listState = items.rememberLazyListState()
            val swipeState = rememberSwipeRefreshState(
                isRefreshing = items.loadState.refresh is LoadState.Loading && items.itemCount > 0,
            )
            SwipeRefresh(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                state = swipeState,
                onRefresh = { items.refresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            ) {
                PagingList(
                    state = listState,
                    items = items,
                    itemContent = { item, _ ->
                        BreedCardItem(
                            breed = item,
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