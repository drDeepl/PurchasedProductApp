package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.responses.UserInfoResponse
import com.mypurchasedproduct.utils.Constants.Companion.USER_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface PurchasedProductApi {

    @GET(USER_ENDPOINT)
    suspend fun getInfoCurrentUser(): Response<UserInfoResponse>
}