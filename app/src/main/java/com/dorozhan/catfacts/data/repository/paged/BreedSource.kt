package com.dorozhan.catfacts.data.repository.paged

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dorozhan.catfacts.data.repository.CatsRepository
import com.dorozhan.catfacts.model.Breed

class BreedSource(
    private val catsRepository: CatsRepository
) : PagingSource<Int, Breed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
        return try {
            val nextPage = params.key ?: 1
            val response = catsRepository.getBreeds(nextPage)

            val list = response.data
            LoadResult.Page(
                data = list.map { it.toBreed() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.nextPageURL == null) null else response.currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Breed>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}