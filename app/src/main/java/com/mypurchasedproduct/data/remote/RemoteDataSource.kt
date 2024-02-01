package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.model.request.AddProductRequest
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.request.RefreshTokenRequest
import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: PurchasedProductAppApi){

    suspend fun signUp(signUpRequest: SignUpRequest) = api.signUp(signUpRequest)
    suspend fun signIn(signInRequest: SignInRequest) = api.signIn(signInRequest)

    suspend fun getAllPurchasedProductsUser(userId:Long, offset: Int) = api.getAllPurchasedProduct(userId, offset)

    suspend fun updateAccessToken(refreshToken: RefreshTokenRequest) = api.refreshToken(refreshToken)

    suspend fun getProducts() = api.getProducts()

    suspend fun getMeasurementUnits() = api.getMeasurementUnits()

    suspend fun addProduct(addProductRequest: AddProductRequest) = api.addProduct(addProductRequest)

    suspend fun getCategories() = api.getCategories()

    suspend fun addPurchasedProduct(addPurchasedProductRequest: AddPurchasedProductRequest) = api.addPurchasedProduct(addPurchasedProductRequest)

    suspend fun deletePurchasedProduct(id:Long) = api.deletePurchasedProduct(id)
}