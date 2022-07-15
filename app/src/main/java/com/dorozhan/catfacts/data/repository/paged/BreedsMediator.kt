package com.dorozhan.catfacts.data.repository.paged

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dorozhan.catfacts.data.local.db.AppDatabase
import com.dorozhan.catfacts.data.local.model.BreedDto
import com.dorozhan.catfacts.data.local.model.BreedRemoteKeysDto
import com.dorozhan.catfacts.data.remote.ApiResponse
import com.dorozhan.catfacts.data.remote.retrofit.Api
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BreedsMediator @Inject constructor(
    private val api: Api,
    private val db: AppDatabase
) : RemoteMediator<Int, BreedDto>() {

    private val breedDao = db.breedDao()
    private val breedRemoteKeysDao = db.breedRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, BreedDto>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = api.getBreeds(pageNumber = page)

            var endOfPaginationReached = false

            when (response) {
                is ApiResponse.Error -> MediatorResult.Error(Throwable("Api Error"))
                is ApiResponse.Success -> {
                    val body = response.body
                    val list = body.data

                    endOfPaginationReached = body.nextPageURL == null
                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            breedDao.deleteAllBreeds()
                            breedRemoteKeysDao.deleteAllBreedRemoteKeys()
                        }

                        val pageNumber = body.currentPage
                        val nextPage = pageNumber + 1
                        val prevPage = if (pageNumber <= 1) null else pageNumber - 1

                        val keys = list.map { breed ->
                            BreedRemoteKeysDto(
                                id = breed.breed,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        breedRemoteKeysDao.addAllBreedRemoteKeys(breedRemoteKeys = keys)
                        breedDao.addBreeds(breeds = list.map { it.toBreed().toDto() })
                    }
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, BreedDto>,
    ): BreedRemoteKeysDto? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { title ->
                breedRemoteKeysDao.getBreedRemoteKeys(id = title)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, BreedDto>,
    ): BreedRemoteKeysDto? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { breed ->
                breedRemoteKeysDao.getBreedRemoteKeys(id = breed.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, BreedDto>,
    ): BreedRemoteKeysDto? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { breed ->
                breedRemoteKeysDao.getBreedRemoteKeys(id = breed.id)
            }
    }
}