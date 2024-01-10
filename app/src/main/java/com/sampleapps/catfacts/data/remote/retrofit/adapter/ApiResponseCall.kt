package com.sampleapps.catfacts.data.remote.retrofit.adapter

import com.sampleapps.catfacts.data.remote.ApiResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class ApiResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<ApiResponse<S, E>> {

    override fun enqueue(callback: Callback<ApiResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                  handleSuccess(body, callback)
                } else {
                   handleError(error, code, callback)
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> ApiResponse.Error.NetworkError(throwable)
                    else -> ApiResponse.Error.UnknownError(throwable)
                }
                callback.onResponse(this@ApiResponseCall, Response.success(networkResponse))
            }
        })
    }

    private fun handleSuccess(body: S?, callback: Callback<ApiResponse<S, E>>){
        if (body != null) {
            callback.onResponse(
                this@ApiResponseCall,
                Response.success(ApiResponse.Success(body))
            )
        } else {
            // Response is successful but the body is null
            callback.onResponse(
                this@ApiResponseCall,
                Response.success(ApiResponse.Error.UnknownError(null))
            )
        }
    }

    private fun handleError(error: ResponseBody?, code: Int, callback: Callback<ApiResponse<S, E>>) {
        val errorBody = when {
            error == null -> null
            error.contentLength() == 0L -> null
            else -> try {
                errorConverter.convert(error)
            } catch (ex: Exception) {
                null
            }
        }
        if (errorBody != null) {
            callback.onResponse(
                this@ApiResponseCall,
                Response.success(ApiResponse.Error.ApiError(errorBody, code))
            )
        } else {
            callback.onResponse(
                this@ApiResponseCall,
                Response.success(ApiResponse.Error.UnknownError(null))
            )
        }
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ApiResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
