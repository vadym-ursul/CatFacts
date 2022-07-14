package com.dorozhan.catfacts.data.remote.retrofit

import com.dorozhan.catfacts.data.remote.ApiResponse
import com.dorozhan.catfacts.data.remote.BREEDS
import com.dorozhan.catfacts.data.remote.PAGE
import com.dorozhan.catfacts.data.remote.model.BreedsResponse
import retrofit2.http.GET
import retrofit2.http.Query

typealias GenericResponse<S> = ApiResponse<S, Unit>

interface Api {

    @GET(BREEDS)
    suspend fun getBreeds(
        @Query(PAGE) pageNumber: Int
    ): GenericResponse<BreedsResponse>
}