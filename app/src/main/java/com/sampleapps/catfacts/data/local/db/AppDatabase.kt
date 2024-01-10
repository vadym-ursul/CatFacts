package com.sampleapps.catfacts.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sampleapps.catfacts.data.local.model.BreedDto
import com.sampleapps.catfacts.data.local.model.BreedRemoteKeysDto

@Database(
    entities = [
        BreedDto::class,
        BreedRemoteKeysDto::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun breedRemoteKeysDao(): BreedRemoteKeysDao
}