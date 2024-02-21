package com.mypurchasedproduct.data.repository

import android.util.Log
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.request.AddProductRequest
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class ProductRepository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource
):BaseApiResponse(){

    private val TAG = this.javaClass.simpleName

    suspend fun getProducts(): NetworkResult<MutableList<ProductResponse>> {
        Log.i(TAG, "GET PRODUCTS")
        return safeApiCall { remoteDataSource.getProducts() }
    }

    suspend fun addProduct(addProductRequest: AddProductRequest): NetworkResult<ProductResponse> {
        Log.i(TAG, "ADD PRODUCT")
        return safeApiCall { remoteDataSource.addProduct(addProductRequest) }
    }

    suspend fun getCategories(): NetworkResult<MutableList<CategoryResponse>>{
        Log.i(TAG, "GET CATEGORIES")
        return safeApiCall { remoteDataSource.getCategories() }
    }



}