package com.sampleapps.catfacts.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sampleapps.catfacts.data.local.model.BreedRemoteKeysDto

@Dao
interface BreedRemoteKeysDao {

    @Query("SELECT * FROM breed_remote_keys WHERE id = :id")
    suspend fun getBreedRemoteKeys(id: String): BreedRemoteKeysDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllBreedRemoteKeys(breedRemoteKeys : List<BreedRemoteKeysDto>)

    @Query("DELETE FROM breed_remote_keys")
    suspend fun deleteAllBreedRemoteKeys()
}