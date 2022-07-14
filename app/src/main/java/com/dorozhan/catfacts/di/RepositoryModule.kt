package com.dorozhan.catfacts.di

import com.dorozhan.catfacts.data.remote.retrofit.Api
import com.dorozhan.catfacts.data.repository.CatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideCatsRepository(
        api: Api,
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): CatsRepository {
        return CatsRepository(api, defaultDispatcher)
    }
}
