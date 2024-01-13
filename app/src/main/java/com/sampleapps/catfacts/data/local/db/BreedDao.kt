package com.sampleapps.catfacts.data.local.db

import androidx.paging.PagingSource
import androidx.room.*
import com.sampleapps.catfacts.data.local.model.BreedDto
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds")
    fun getBreedsPaged(): PagingSource<Int, BreedDto>

    @Query("SELECT * FROM breeds WHERE id LIKE '%' || :text || '%'")
    fun getFilteredBreedsPaged(text: String): PagingSource<Int, BreedDto>

    @Query("SELECT COUNT(id) FROM breeds")
    suspend fun getBreedsCount(): Int

    @Query("SELECT * FROM breeds WHERE favorite=:favorite")
    fun findByFavorite(favorite: Boolean = true): PagingSource<Int, BreedDto>

    @Query("SELECT * FROM breeds WHERE id=:id")
    fun findById(id: String): Flow<BreedDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBreeds(breeds: List<BreedDto>)

    @Query("DELETE FROM breeds")
    suspend fun deleteAllBreeds()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBreed(breed: BreedDto)
}