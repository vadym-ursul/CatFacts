package com.sampleapps.catfacts.presentation.flow.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sampleapps.catfacts.R
import com.sampleapps.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.sampleapps.catfacts.presentation.library.BackAppBar
import com.sampleapps.catfacts.presentation.library.BreedCardItem
import com.sampleapps.catfacts.presentation.library.PagingList
import com.sampleapps.catfacts.presentation.util.rememberLazyGridState
import com.sampleapps.catfacts.presentation.util.setSystemBarsColor

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
) {
    val statusBarColor = MaterialTheme.colorScheme.surfaceVariant
    val isSystemInDarkTheme = isSystemInDarkTheme()

    setSystemBarsColor(statusBarColor, statusBarColor.copy(alpha = 0.5f), !isSystemInDarkTheme)

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            BackAppBar(
                text = stringResource(id = R.string.favorites),
                onBackClick = { navigator.navigateUp() })
        },
        content = { paddings ->
            val items = favoritesViewModel.favoriteBreedsFlow.collectAsLazyPagingItems()
            val listState = items.rememberLazyGridState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddings)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                PagingList(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    items = items,
                    itemContent = { item, _ ->
                        BreedCardItem(
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