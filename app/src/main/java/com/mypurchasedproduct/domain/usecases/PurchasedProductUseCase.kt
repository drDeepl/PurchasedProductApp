package com.mypurchasedproduct.domain.usecases

import android.util.Log
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class PurchasedProductUseCase @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository,
    private val tokenUseCase: TokenUseCase,
) {

    private val TAG: String = this.javaClass.simpleName

    suspend fun getAllPurchasedProductsCurrentUser(userId: Long, offset: Int) = purchasedProductRepository.getAllPurchasedProductUser(userId, offset)
    suspend fun addPurchasedProduct(addPurchasedProductItem: AddPurchasedProductItem): NetworkResult<PurchasedProductResponse>{
        TODO("ADD GET ACCESS TOKEN ACTION")
        Log.wtf(TAG, "ADD PURCHASED PRODUCT")
    }
}