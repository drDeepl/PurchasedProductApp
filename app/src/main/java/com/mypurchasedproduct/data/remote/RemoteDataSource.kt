package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: PurchasedProductAppApi){

    suspend fun signUp(signUpRequest: SignUpRequest) = api.signUp(signUpRequest)
    suspend fun signIn(signInRequest: SignInRequest) = api.signIn(signInRequest)

    suspend fun getAllPurchasedProductsUser(userId:Long, offset: Int) = api.getAllPurchasedProduct(userId, offset)
}