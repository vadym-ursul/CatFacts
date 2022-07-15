package com.dorozhan.catfacts.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dorozhan.catfacts.domain.model.Breed

@Entity(tableName = "breeds")
class BreedDto(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "isFavourite") val isFavourite: Boolean,
) {

    fun toBreed(): Breed {
        return Breed(title = id, imageUrl = imageUrl, isFavourite = isFavourite)
    }
}