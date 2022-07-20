package com.dorozhan.catfacts.domain.model

import com.dorozhan.catfacts.data.local.model.BreedDto

data class Breed(
    val title: String,
    val imageUrl: String,
    val favorite: Boolean = false
) {

    fun toDto(): BreedDto {
        return BreedDto(id = title, imageUrl = imageUrl, favorite = favorite)
    }
}