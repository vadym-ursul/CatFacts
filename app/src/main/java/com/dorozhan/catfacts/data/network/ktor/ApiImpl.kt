package com.dorozhan.catfacts.data.network.ktor

import com.dorozhan.catfacts.data.network.BREEDS
import com.dorozhan.catfacts.data.network.CATFACT_NINJA_URL
import com.dorozhan.catfacts.data.network.PAGE
import com.dorozhan.catfacts.model.BreedsResponse
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