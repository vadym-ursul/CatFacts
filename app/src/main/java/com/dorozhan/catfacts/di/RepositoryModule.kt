package com.dorozhan.catfacts.di

import com.dorozhan.catfacts.data.local.db.BreedDao
import com.dorozhan.catfacts.data.remote.retrofit.Api
import com.dorozhan.catfacts.data.repository.BreedsRepository
import com.dorozhan.catfacts.data.repository.paged.BreedsMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideBreedsRepository(
        api: Api,
        breedDao: BreedDao,
        breedsMediator: BreedsMediator,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): BreedsRepository {
        return BreedsRepository(api, breedDao, breedsMediator, defaultDispatcher)
    }
}
