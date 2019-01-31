package com.example.demodatingapp.network.api.response

import retrofit2.Response

sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                ApiSuccessResponse(response.body()!!)
            } else {
                val message = response.errorBody()?.string()
                val errorMessage = if (message.isNullOrEmpty()) {
                    response.message()
                } else {
                    message
                }
                ApiErrorResponse(errorMessage ?: "unknown error")
            }
        }
    }
}

data class ApiSuccessResponse<T>(val body: T): ApiResponse<T>()
data class ApiErrorResponse<T>(val errorMessage: String): ApiResponse<T>()