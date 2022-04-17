package com.dorozhan.catfacts.data.network.retrofit

import com.dorozhan.catfacts.data.network.BREEDS
import com.dorozhan.catfacts.data.network.PAGE
import com.dorozhan.catfacts.model.BreedsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(BREEDS)
    suspend fun getBreeds(
        @Query(PAGE) pageNumber: Int
    ): BreedsResponse
}