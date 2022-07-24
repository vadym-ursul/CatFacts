package com.dorozhan.catfacts.presentation.library

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.dorozhan.catfacts.presentation.state.*

@Composable
fun <T : Any> PagingList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    items: LazyPagingItems<T>,
    itemContent: @Composable LazyListScope.(T) -> Unit,
) {
    LazyColumn(modifier = modifier, state = state) {
        items(items) { item ->
            item?.let {
                this@LazyColumn.itemContent(it)
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
                loadState.append is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        items.itemCount <= 0 -> {
                    item { EmptyItemView(modifier = Modifier.fillParentMaxSize()) }
                }
            }
        }
    }
}