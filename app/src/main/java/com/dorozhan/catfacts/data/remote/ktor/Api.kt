package com.dorozhan.catfacts.data.remote.ktor

import com.dorozhan.catfacts.data.remote.model.BreedsResponse

interface Api {
    suspend fun getBreeds(pageNumber: Int): BreedsResponse
}
