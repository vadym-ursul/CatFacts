package com.sampleapps.catfacts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor.Logger
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @DefaultLogger
    @Provides
    fun provideDefaultHttpLogger(): Logger = Logger.DEFAULT
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultLogger