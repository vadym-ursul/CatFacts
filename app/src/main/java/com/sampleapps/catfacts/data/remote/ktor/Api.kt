package com.sampleapps.catfacts.data.remote.ktor

import com.sampleapps.catfacts.data.remote.model.BreedsResponse

interface Api {
    suspend fun getBreeds(pageNumber: Int): BreedsResponse
}
