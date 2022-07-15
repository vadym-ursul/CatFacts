package com.dorozhan.catfacts.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dorozhan.catfacts.data.local.model.BreedDto

@Dao
interface BreedDao {

    @Query("SELECT * FROM breed")
    fun getAll(): List<BreedDto>

    @Query("SELECT * FROM breed WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<BreedDto>

    @Query("SELECT * FROM breed WHERE title LIKE :first LIMIT 1")
    fun findByName(first: String, last: String): BreedDto

    @Insert
    fun insertAll(vararg users: BreedDto)

    @Delete
    fun delete(user: BreedDto)
}