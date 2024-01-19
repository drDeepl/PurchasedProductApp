package com.mypurchasedproduct.presentation.utils

import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun<T> safeApiCall(api: suspend () -> Response<T>): NetworkResult<T>{
        try {
            val response = api()
            if(response.isSuccessful){
                val body = response.body()
                body?. let{
                    return NetworkResult.Success(body)
                } ?: return errorMessage("Body is empty")
            }else{
                return errorMessage("${response.message()}", response.code())
            }

        }
        catch (e:Exception){
            return errorMessage("Api called failed ${e.message.toString()}")
        }
    }

    private fun <T> errorMessage(errorMessage: String, statusCode: Int? = null): NetworkResult.Error<T>{
        return NetworkResult.Error(errorMessage, statusCode=statusCode)
    }
}