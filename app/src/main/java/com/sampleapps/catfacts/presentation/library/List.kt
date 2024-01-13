package com.sampleapps.catfacts.presentation.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sampleapps.catfacts.presentation.state.EmptyItemView
import com.sampleapps.catfacts.presentation.state.ErrorItem
import com.sampleapps.catfacts.presentation.state.ErrorView
import com.sampleapps.catfacts.presentation.state.LoadingItem
import com.sampleapps.catfacts.presentation.state.LoadingView

@Composable
fun <T : Any> PagingList(
    modifier: Modifier = Modifier,
    state: LazyGridState,
    items: LazyPagingItems<T>,
    isListLayout: Boolean = true,
    itemContent: @Composable LazyGridScope.(T, Int) -> Unit,
) {
    val columnsCount = if (isListLayout) 1 else 3

    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        columns = GridCells.Fixed(columnsCount),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.itemCount) { index ->
            items[index]?.let {
                this@LazyVerticalGrid.itemContent(it, index)
            }
        }
        items.apply {
            when {
                loadState.refresh is LoadState.Loading && items.itemCount <= 0 -> {
                    item { LoadingView(modifier = Modifier.fillMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    // todo: handle error with using text
                    val e = items.loadState.refresh as LoadState.Error
                    item {
                        ErrorView(
                            modifier = Modifier.fillMaxSize(),
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

                loadState.append is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        items.itemCount <= 0 -> {
                    item { EmptyItemView(modifier = Modifier.fillMaxSize()) }
                }
            }
        }
    }
}