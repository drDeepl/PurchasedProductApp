package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.dto.UserInfoDto
import com.mypurchasedproduct.presentation.utils.Constants.Companion.USER_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface PurchasedProductApi {

    @GET(USER_ENDPOINT)
    suspend fun getInfoCurrentUser(): Response<UserInfoDto>
}