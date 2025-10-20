package com.sulsoftware.cmp_weather.network

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Throwable, val message: String? = null) : ApiResult<Nothing>()
}
