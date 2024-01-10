package com.sampleapps.catfacts.data.remote.retrofit

import com.sampleapps.catfacts.data.remote.ApiResponse
import com.sampleapps.catfacts.data.remote.BREEDS
import com.sampleapps.catfacts.data.remote.PAGE
import com.sampleapps.catfacts.data.remote.model.BreedsResponse
import retrofit2.http.GET
import retrofit2.http.Query

typealias GenericResponse<S> = ApiResponse<S, Unit>

interface Api {

    @GET(BREEDS)
    suspend fun getBreeds(
        @Query(PAGE) pageNumber: Int
    ): GenericResponse<BreedsResponse>
}