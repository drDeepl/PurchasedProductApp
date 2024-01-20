package com.mypurchasedproduct.presentation.utils

sealed class NetworkResult<T> (val data: T? = null, val message: String? = null, val statusCode: Int? = null){
    class Success<T> (data: T): NetworkResult<T>(data)
    class Error<T> (message: String, data: T? = null, statusCode: Int? = null): NetworkResult<T>(data, message, statusCode)

}