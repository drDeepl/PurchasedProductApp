package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.dto.MessageDto
import com.mypurchasedproduct.data.remote.dto.SignUpDto
import com.mypurchasedproduct.data.remote.dto.UserInfoDto
import com.mypurchasedproduct.presentation.utils.Constants.Companion.USER_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface PurchasedProductApi {

    @GET(USER_ENDPOINT)
    suspend fun getInfoCurrentUser(): Response<UserInfoDto>

    @POST("USER_ENDPOINT/signUp")
    suspend fun signUp(signUpDto: SignUpDto): Response<MessageDto>

}