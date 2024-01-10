package com.sampleapps.catfacts.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "breed_remote_keys")
data class BreedRemoteKeysDto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)
