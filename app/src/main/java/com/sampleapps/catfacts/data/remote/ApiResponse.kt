package com.sampleapps.catfacts.data.remote

import java.io.IOException

sealed class ApiResponse<out T : Any, out E : Any?> {
    data class Success<T : Any>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<out E : Any> : ApiResponse<Nothing, E>() {
        data class ApiError<E : Any>(val body: E, val code: Int) : Error<E>()
        data class NetworkError(val error: IOException) : Error<Nothing>()
        data class UnknownError(val error: Throwable?) : Error<Nothing>()
    }
}
