package com.dorozhan.catfacts.data.local.db

import androidx.paging.PagingSource
import androidx.room.*
import com.dorozhan.catfacts.data.local.model.BreedDto

@Dao
interface BreedDao {

    @Query("SELECT * FROM breeds")
    fun getAllBreeds(): PagingSource<Int, BreedDto>

    @Query("SELECT * FROM breeds WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<BreedDto>

    @Query("SELECT * FROM breeds WHERE id LIKE :first LIMIT 1")
    fun findByName(first: String): BreedDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBreeds(breeds: List<BreedDto>)

    @Insert
    fun insertAll(vararg users: BreedDto)

    @Delete
    fun delete(user: BreedDto)

    @Query("DELETE FROM breeds")
    suspend fun deleteAllBreeds()
}