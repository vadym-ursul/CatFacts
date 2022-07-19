package com.dorozhan.catfacts.presentation.screen.catscatalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.domain.model.Breed
import com.dorozhan.catfacts.presentation.screen.destinations.CatDetailsScreenDestination
import com.dorozhan.catfacts.presentation.screen.destinations.SearchScreenDestination
import com.dorozhan.catfacts.presentation.state.ErrorItem
import com.dorozhan.catfacts.presentation.state.ErrorView
import com.dorozhan.catfacts.presentation.state.LoadingItem
import com.dorozhan.catfacts.presentation.state.LoadingView
import com.dorozhan.catfacts.presentation.util.rememberLazyListState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow

@RootNavGraph(start = true)
@Destination
@Composable
fun CatsCatalogScreen(
    navigator: DestinationsNavigator,
    catsCatalogViewModel: CatsCatalogViewModel = hiltViewModel(),
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
            List(
                breeds = catsCatalogViewModel.breedsFlow,
                onBreedItemClick = {
                    navigator.navigate(CatDetailsScreenDestination(breedName = it.title))
                }
            )
        }
    )
}

@Composable
private fun List(
    breeds: Flow<PagingData<Breed>>,
    onBreedItemClick: (Breed) -> Unit = {},
) {
    val lazyItems = breeds.collectAsLazyPagingItems()

    LazyColumn(state = lazyItems.rememberLazyListState()) {
        items(lazyItems) { movie ->
            movie?.let { BreedItem(breed = it, onBreedItemClick = onBreedItemClick) }
        }
        lazyItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    // todo: handle error with using text
                    val e = lazyItems.loadState.refresh as LoadState.Error
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
        // todo: replace placeholder image with cat image
        AsyncImage(
            model = breed.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_cat_placeholder_512),
            error = painterResource(id = R.drawable.ic_cat_placeholder_512),
        )
    }
}

@Composable
@Preview
private fun BreedItemPreview() {
    BreedItem(breed = Breed("Persian", imageUrl = ""))
}