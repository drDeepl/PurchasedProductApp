package com.mypurchasedproduct.data.repository

import android.util.Log
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.request.EditPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.domain.model.EditPurchasedProductModel
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import java.sql.Timestamp
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

    suspend fun editPurchasedProduct(id: Long, editPurchasedProductRequest: EditPurchasedProductRequest): NetworkResult<PurchasedProductResponse>{
        return safeApiCall { remoteDataSource.editPurchasedProduct(id, editPurchasedProductRequest) }
    }

    suspend fun deletePurchasedProduct(id: Long): NetworkResult<MessageResponse>{
        Log.w(TAG, "DELETE PURCHASED PRODUCT")
        return safeApiCall { remoteDataSource.deletePurchasedProduct(id) }
    }

    suspend fun getPurchasedProductsByDate(timestamp: Long): NetworkResult<List<PurchasedProductResponse>>{
        Log.w(TAG, "GET PURCHASED PRODUCTS BY DATE")
        return safeApiCall { remoteDataSource.getPurchasedProductsByDate(timestamp) }
    }

}