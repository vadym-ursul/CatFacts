package com.sampleapps.catfacts.domain.model

import com.sampleapps.catfacts.data.local.model.BreedDto

data class Breed(
    val title: String,
    val imageUrl: String,
    val favorite: Boolean = false
) {

    fun toDto(): BreedDto {
        return BreedDto(id = title, imageUrl = imageUrl, favorite = favorite)
    }
}