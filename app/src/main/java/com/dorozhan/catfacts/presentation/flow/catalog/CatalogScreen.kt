package com.dorozhan.catfacts.presentation.flow.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.domain.model.Breed
import com.dorozhan.catfacts.presentation.flow.destinations.CatDetailsScreenDestination
import com.dorozhan.catfacts.presentation.flow.destinations.SearchScreenDestination
import com.dorozhan.catfacts.presentation.state.ErrorItem
import com.dorozhan.catfacts.presentation.state.ErrorView
import com.dorozhan.catfacts.presentation.state.LoadingItem
import com.dorozhan.catfacts.presentation.state.LoadingView
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
        content = {
            val items = catalogViewModel.breedsFlow.collectAsLazyPagingItems()
            val listState = items.rememberLazyListState()
            val swipeState = rememberSwipeRefreshState(
                isRefreshing = items.loadState.refresh is LoadState.Loading && items.itemCount > 0,
            )
            SwipeRefresh(
                modifier = Modifier.fillMaxSize(),
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
                List(
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

@Composable
private fun List(
    state: LazyListState,
    items: LazyPagingItems<Breed>,
    onBreedItemClick: (Breed) -> Unit = {},
    onFavoriteClick: (Breed, Boolean) -> Unit = { _, _ -> }
) {
    LazyColumn(state = state) {
        items(items) { movie ->
            movie?.let {
                BreedItem(
                    breed = it,
                    onBreedItemClick = onBreedItemClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
        items.apply {
            when {
                loadState.refresh is LoadState.Loading && items.itemCount <= 0 -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    // todo: handle error with using text
                    val e = items.loadState.refresh as LoadState.Error
                    item {
                        ErrorView(
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        ErrorItem(
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BreedItem(
    breed: Breed,
    onBreedItemClick: (Breed) -> Unit = {},
    onFavoriteClick: (Breed, Boolean) -> Unit = { _, _ -> }
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable { onBreedItemClick(breed) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = breed.title,
            maxLines = 1,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            Modifier
                .padding(start = 16.dp)
                .size(100.dp)
        ) {
            AsyncImage(
                model = breed.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_cat_placeholder_512),
                error = painterResource(id = R.drawable.ic_cat_placeholder_512),
            )
            FavoriteButton(
                modifier = Modifier.align(Alignment.TopEnd),
                checked = breed.favorite,
                onCheckedChange = { onFavoriteClick(breed, it) }
            )
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color = MaterialTheme.colors.primary
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            onCheckedChange.invoke(it)
        },
        modifier = modifier
    ) {
        Icon(
            tint = color,
            imageVector = if (checked) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}

@Composable
@Preview
private fun FavoriteButtonPreview() {
    FavoriteButton(
        checked = true,
        onCheckedChange = {}
    )
}

@Composable
@Preview
private fun BreedItemPreview() {
    BreedItem(breed = Breed("Persian", imageUrl = ""))
}