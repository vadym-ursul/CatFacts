package com.dorozhan.catfacts.data.network.ktor

import com.dorozhan.catfacts.model.BreedsResponse

interface Api {
    suspend fun getBreeds(pageNumber: Int): BreedsResponse
}
