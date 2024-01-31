package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.model.request.AddProductRequest
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.request.RefreshTokenRequest
import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.data.remote.model.response.UserInfoResponse
import com.mypurchasedproduct.presentation.utils.Constants.Companion.MEASUREMENT_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.PRODUCT_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.PURCHASED_PRODUCT_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.USER_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PurchasedProductAppApi {

    @GET(USER_ENDPOINT)
    suspend fun getInfoCurrentUser(): Response<UserInfoResponse>

    @POST("${USER_ENDPOINT}/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<MessageResponse>

    @POST("${USER_ENDPOINT}/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<TokenResponse>

    @GET("${PURCHASED_PRODUCT_ENDPOINT}/all/{user_id}")
    suspend fun getAllPurchasedProduct(@Path("user_id") userId: Long, @Query("offset") offset: Int): Response<List<PurchasedProductResponse>>

    @POST("${PURCHASED_PRODUCT_ENDPOINT}/add")
    suspend fun addPurchasedProduct(@Body addPurchasedProductRequest: AddPurchasedProductRequest): Response<PurchasedProductResponse>

    @POST("${USER_ENDPOINT}/refreshToken")
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Response<TokenResponse>

    @GET("${PRODUCT_ENDPOINT}/all")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @POST("${PRODUCT_ENDPOINT}/add")
    suspend fun addProduct(@Body addProductRequest: AddProductRequest): Response<ProductResponse>

    @GET("${MEASUREMENT_ENDPOINT}/all")
    suspend fun getMeasurementUnits(): Response<List<MeasurementUnitResponse>>

    @GET("${PRODUCT_ENDPOINT}/category/all")
    suspend fun getCategories(): Response<List<CategoryResponse>>
}