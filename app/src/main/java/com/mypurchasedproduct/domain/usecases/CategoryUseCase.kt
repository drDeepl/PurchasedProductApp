package com.mypurchasedproduct.domain.usecases

import android.util.Log
import com.mypurchasedproduct.data.remote.model.request.AddCategoryRequest
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.repository.CategoryRepository
import com.mypurchasedproduct.data.repository.MeasurementUnitRepository
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    private val TAG: String = this.javaClass.simpleName

    suspend fun addCategory(name: String): NetworkResult<CategoryResponse> {
        Log.wtf(TAG, "ADD CATEGORY")
        return categoryRepository.addCategory(AddCategoryRequest(name))
    }
}