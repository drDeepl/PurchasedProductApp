package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.data.remote.model.response.UserInfoResponse
import com.mypurchasedproduct.presentation.utils.Constants.Companion.USER_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PurchasedProductApi {

    @GET(USER_ENDPOINT)
    suspend fun getInfoCurrentUser(): Response<UserInfoResponse>

    @POST("${USER_ENDPOINT}/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<MessageResponse>

    @POST("${USER_ENDPOINT}/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<TokenResponse>
}