package com.sampleapps.catfacts.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sampleapps.catfacts.domain.model.Breed

@Entity(tableName = "breeds")
class BreedDto(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "favorite") val favorite: Boolean,
) {

    fun toBreed(): Breed {
        return Breed(title = id, imageUrl = imageUrl, favorite = favorite)
    }
}