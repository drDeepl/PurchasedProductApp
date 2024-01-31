package com.mypurchasedproduct.data.repository

import android.util.Log
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class PurchasedProductRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
): BaseApiResponse() {
    private val TAG = this.javaClass.simpleName

    suspend fun getAllPurchasedProductUser(userId: Long, offset: Int) : NetworkResult<List<PurchasedProductResponse>>{
        Log.i(TAG, "GET ALL PURCHASED PRODUCT OF USER")
        return safeApiCall{remoteDataSource.getAllPurchasedProductsUser(userId, offset)}
    }

    suspend fun addPurchasedProduct(addPurchasedProductRequest: AddPurchasedProductRequest): NetworkResult<PurchasedProductResponse>{
        Log.w(TAG,"ADD PURCHASED PRODUCT")
        return safeApiCall { remoteDataSource.addPurchasedProduct(addPurchasedProductRequest) }
    }

}