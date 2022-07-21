package com.dorozhan.catfacts.presentation.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.domain.model.Breed
import com.dorozhan.catfacts.presentation.state.ErrorItem
import com.dorozhan.catfacts.presentation.state.ErrorView
import com.dorozhan.catfacts.presentation.state.LoadingItem
import com.dorozhan.catfacts.presentation.state.LoadingView

@Composable
fun BreedsLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState,
    items: LazyPagingItems<Breed>,
    onBreedItemClick: (Breed) -> Unit = {},
    onFavoriteClick: (Breed, Boolean) -> Unit = { _, _ -> }
) {
    LazyColumn(modifier = modifier, state = state) {
        items(items) { movie ->
            movie?.let {
                BreedItem(
                    breed = it,
                    onBreedItemClick = onBreedItemClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
        val loadState = items.loadState
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
                        onClickRetry = { items.retry() }
                    )
                }
            }
            loadState.append is LoadState.Error -> {
                item {
                    ErrorItem(
                        onClickRetry = { items.retry() }
                    )
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
@Preview
private fun BreedItemPreview() {
    BreedItem(breed = Breed("Persian", imageUrl = ""))
}