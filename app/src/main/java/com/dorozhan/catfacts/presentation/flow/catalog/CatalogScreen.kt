package com.dorozhan.catfacts.presentation.flow.catalog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.dorozhan.catfacts.presentation.flow.destinations.SearchScreenDestination
import com.dorozhan.catfacts.presentation.library.BreedsLazyColumn
import com.dorozhan.catfacts.presentation.util.rememberLazyListState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun CatsCatalogScreen(
    navigator: DestinationsNavigator,
    catalogViewModel: CatalogViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.breeds)) },
                actions = {
                    IconButton(
                        onClick = {
                            navigator.navigate(SearchScreenDestination())
                        }
                    ) {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                    }
                }
            )
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
                BreedsLazyColumn(
                    state = listState,
                    items = items,
                    onBreedItemClick = {
                        navigator.navigate(CatDetailsScreenDestination(breedName = it.title))
                    },
                    onFavoriteClick = { breed, checked ->
                        catalogViewModel.onFavoriteClicked(breed, checked)
                    }
                )
            }
        }
    )
}