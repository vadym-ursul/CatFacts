package com.sampleapps.catfacts.di

import com.sampleapps.catfacts.data.local.Storage
import com.sampleapps.catfacts.data.local.db.BreedDao
import com.sampleapps.catfacts.data.remote.retrofit.Api
import com.sampleapps.catfacts.data.repository.BreedsRepository
import com.sampleapps.catfacts.data.repository.OnboardRepository
import com.sampleapps.catfacts.data.repository.paged.BreedsMediator
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

    @Provides
    fun provideOnboardRepository(
        storage: Storage,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): OnboardRepository {
        return OnboardRepository(storage, defaultDispatcher)
    }
}
