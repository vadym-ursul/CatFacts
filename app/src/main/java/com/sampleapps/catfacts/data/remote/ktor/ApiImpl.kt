package com.sampleapps.catfacts.data.remote.ktor

import com.sampleapps.catfacts.data.remote.BREEDS
import com.sampleapps.catfacts.data.remote.CATFACT_NINJA_URL
import com.sampleapps.catfacts.data.remote.PAGE
import com.sampleapps.catfacts.data.remote.model.BreedsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiImpl(private val client: HttpClient) : Api {

    override suspend fun getBreeds(pageNumber: Int): BreedsResponse {
        return client.get {
            catFactApi(BREEDS)
            parameter(PAGE, pageNumber)
        }.body()
    }

    private fun HttpRequestBuilder.catFactApi(path: String) {
        url {
            takeFrom(CATFACT_NINJA_URL)
            encodedPath = path
        }
    }
}