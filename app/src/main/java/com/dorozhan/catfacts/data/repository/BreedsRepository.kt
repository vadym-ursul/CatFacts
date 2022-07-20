package com.dorozhan.catfacts.data.repository

import androidx.paging.*
import com.dorozhan.catfacts.data.local.db.BreedDao
import com.dorozhan.catfacts.data.local.model.BreedDto
import com.dorozhan.catfacts.data.remote.retrofit.Api
import com.dorozhan.catfacts.data.repository.paged.BreedsMediator
import com.dorozhan.catfacts.di.IoDispatcher
import com.dorozhan.catfacts.domain.model.Breed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BreedsRepository @Inject constructor(
    private val api: Api,
    private val breedDao: BreedDao,
    private val breedsMediator: BreedsMediator,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getBreeds(): Flow<PagingData<Breed>> {
        val pagingSourceFactory: () -> PagingSource<Int, BreedDto> = { breedDao.getBreedsPaged() }
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 0),
            remoteMediator = breedsMediator,
            pagingSourceFactory = pagingSourceFactory,
        ).flow.map { pagingData ->
            pagingData.map { breedDto -> breedDto.toBreed() }
        }
    }

    suspend fun getBreedByName(name: String): Breed {
        return withContext(defaultDispatcher) {
            breedDao.findById(name).toBreed()
        }
    }

    suspend fun setFavorite(breed: Breed, favorite: Boolean) {
        return withContext(defaultDispatcher) {
            breedDao.updateBreed(breed.copy(favorite = favorite).toDto())
        }
    }
}