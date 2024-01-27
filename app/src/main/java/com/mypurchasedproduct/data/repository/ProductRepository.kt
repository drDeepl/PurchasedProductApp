package com.mypurchasedproduct.data.repository

import android.util.Log
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class ProductRepository  @Inject constructor(
    private val remoteDataSource: RemoteDataSource
):BaseApiResponse(){

    private val TAG = this.javaClass.simpleName

    suspend fun getProducts(): NetworkResult<List<ProductResponse>> {
        Log.i(TAG, "GET PRODUCTS")
        return safeApiCall { remoteDataSource.getProducts() }
    }



}