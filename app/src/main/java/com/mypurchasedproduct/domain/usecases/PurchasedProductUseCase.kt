package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import javax.inject.Inject

class PurchasedProductUseCase @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository
) {

    suspend fun getAllPurchasedProductsCurrentUser(userId: Long, offset: Int) = purchasedProductRepository.getAllPurchasedProductUser(userId, offset)
}