package com.dorozhan.catfacts.presentation.flow.catalog

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.dorozhan.catfacts.presentation.flow.destinations.FavoritesScreenDestination
import com.dorozhan.catfacts.presentation.flow.destinations.SearchScreenDestination
import com.dorozhan.catfacts.presentation.library.BreedItem
import com.dorozhan.catfacts.presentation.library.CatalogAppBar
import com.dorozhan.catfacts.presentation.library.PagingList
import com.dorozhan.catfacts.presentation.util.rememberLazyListState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun CatalogScreen(
    navigator: DestinationsNavigator,
    catalogViewModel: CatalogViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            CatalogAppBar(title = stringResource(id = R.string.breeds),
                onSearchClick = { navigator.navigate(SearchScreenDestination()) },
                onFavoritesClick = { navigator.navigate(FavoritesScreenDestination) })
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
                    .padding(padding),
                state = swipeState,
                onRefresh = { items.refresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        contentColor = MaterialTheme.colors.primary
                    )
                }
            ) {
                PagingList(
                    state = listState,
                    items = items,
                    itemContent = { item ->
                        BreedItem(
                            breed = item,
                            onItemClick = {
                                navigator.navigate(CatDetailsScreenDestination(breedName = it.title))
                            },
                            onFavoriteClick = { breed, checked ->
                                catalogViewModel.onFavoriteClicked(breed, checked)
                            }
                        )
                    })
            }
        }
    )
}