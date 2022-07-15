package com.dorozhan.catfacts.di

import android.content.Context
import androidx.room.Room
import com.dorozhan.catfacts.data.local.db.AppDatabase
import com.dorozhan.catfacts.data.local.db.BreedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "CatFacts"
        ).build()
    }

    @Provides
    fun provideBreedDao(appDatabase: AppDatabase): BreedDao {
        return appDatabase.breedDao()
    }
}