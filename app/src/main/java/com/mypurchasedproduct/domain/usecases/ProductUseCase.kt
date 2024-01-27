package com.mypurchasedproduct.domain.usecases

import android.net.Network
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.repository.ProductRepository
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
){
    private val TAG: String = this.javaClass.simpleName

    suspend fun getProducts(): NetworkResult<List<ProductResponse>> = productRepository.getProducts()
}