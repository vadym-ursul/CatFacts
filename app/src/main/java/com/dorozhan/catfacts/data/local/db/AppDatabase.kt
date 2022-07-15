package com.dorozhan.catfacts.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dorozhan.catfacts.data.local.model.BreedDto

@Database(entities = [BreedDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}