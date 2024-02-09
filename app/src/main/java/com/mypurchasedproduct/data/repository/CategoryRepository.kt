package com.mypurchasedproduct.data.repository

import android.util.Log
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.request.AddCategoryRequest
import com.mypurchasedproduct.data.remote.model.request.AddProductRequest
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): BaseApiResponse() {

    private val TAG: String = this.javaClass.simpleName

    suspend fun addCategory(addCategoryRequest: AddCategoryRequest): NetworkResult<CategoryResponse> {
        Log.i(TAG, "ADD PRODUCT")
        return safeApiCall { remoteDataSource.addCategory(addCategoryRequest) }
    }
}