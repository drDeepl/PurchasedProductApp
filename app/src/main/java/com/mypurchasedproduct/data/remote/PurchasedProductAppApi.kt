package com.mypurchasedproduct.data.remote

import com.mypurchasedproduct.data.remote.model.request.AddCategoryRequest
import com.mypurchasedproduct.data.remote.model.request.AddMeasurementUnitRequest
import com.mypurchasedproduct.data.remote.model.request.AddProductRequest
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.request.EditPurchasedProductRequest
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
import com.mypurchasedproduct.presentation.utils.Constants.Companion.AUTH_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.MEASUREMENT_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.PRODUCT_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.PURCHASED_PRODUCT_ENDPOINT
import com.mypurchasedproduct.presentation.utils.Constants.Companion.USER_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.sql.Timestamp

interface PurchasedProductAppApi {


    @POST("${AUTH_ENDPOINT}/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<MessageResponse>

    @POST("${AUTH_ENDPOINT}/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<TokenResponse>

    @GET("${PURCHASED_PRODUCT_ENDPOINT}/all/{user_id}")
    suspend fun getAllPurchasedProduct(@Path("user_id") userId: Long, @Query("offset") offset: Int): Response<List<PurchasedProductResponse>>

    @GET("${PURCHASED_PRODUCT_ENDPOINT}/date")
    suspend fun getPurchasedProductsByDate(@Query("timestamp") timestamp: Long): Response<List<PurchasedProductResponse>>

    @POST("${PURCHASED_PRODUCT_ENDPOINT}/add")
    suspend fun addPurchasedProduct(@Body addPurchasedProductRequest: AddPurchasedProductRequest): Response<PurchasedProductResponse>

    @DELETE("${PURCHASED_PRODUCT_ENDPOINT}/delete/{purchased_product_id}")
    suspend fun deletePurchasedProduct(@Path("purchased_product_id") purchasedProductId: Long): Response<MessageResponse>

//    @POST("${USER_ENDPOINT}/refreshtoken")
//    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<TokenResponse>

    @POST("${AUTH_ENDPOINT}/refresh")
    suspend fun updateTokens(@Header("Authorization") refreshToken: String): Response<TokenResponse>


    @GET("${PRODUCT_ENDPOINT}/all")
    suspend fun getProducts(): Response<MutableList<ProductResponse>>

    @POST("${PRODUCT_ENDPOINT}/add")
    suspend fun addProduct(@Body addProductRequest: AddProductRequest): Response<ProductResponse>

    @POST("${PURCHASED_PRODUCT_ENDPOINT}/edit/{id}")
    suspend fun editPurchasedProduct(@Path("id") id: Long, @Body() editPurchasedProductRequest: EditPurchasedProductRequest): Response<PurchasedProductResponse>

    @GET("${MEASUREMENT_ENDPOINT}/all")
    suspend fun getMeasurementUnits(): Response<MutableList<MeasurementUnitResponse>>

    @POST("${MEASUREMENT_ENDPOINT}/add")
    suspend fun createMeasurementUnit(request: AddMeasurementUnitRequest): Response<MeasurementUnitResponse>

    @GET("${PRODUCT_ENDPOINT}/category/all")
    suspend fun getCategories(): Response<MutableList<CategoryResponse>>

    @POST("${PRODUCT_ENDPOINT}/category/add")
    suspend fun addCategory(@Body() addCategoryRequest: AddCategoryRequest): Response<CategoryResponse>


}