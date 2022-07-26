package com.dorozhan.catfacts.presentation.flow.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.dorozhan.catfacts.presentation.library.BackAppBar
import com.dorozhan.catfacts.presentation.library.BreedListItem
import com.dorozhan.catfacts.presentation.library.PagingList
import com.dorozhan.catfacts.presentation.util.rememberLazyListState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            BackAppBar(
                text = stringResource(id = R.string.favorites),
                onBackClick = { navigator.navigateUp() })
        },
        content = {
            val items = favoritesViewModel.favoriteBreedsFlow.collectAsLazyPagingItems()
            val listState = items.rememberLazyListState()
            Column(modifier = Modifier.fillMaxSize()) {
                PagingList(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    items = items,
                    itemContent = { item, _ ->
                        BreedListItem(
                            breed = item,
                            isFavoriteVisible = false,
                            onItemClick = {
                                navigator.navigate(CatDetailsScreenDestination(breedName = it.title))
                            }
                        )
                    })
            }
        }
    )
}