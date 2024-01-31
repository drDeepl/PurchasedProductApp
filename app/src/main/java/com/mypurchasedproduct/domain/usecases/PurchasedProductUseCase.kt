package com.mypurchasedproduct.domain.usecases

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import java.sql.Timestamp
import java.time.Instant
import javax.inject.Inject

class PurchasedProductUseCase @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository,
    private val tokenUseCase: TokenUseCase,
) {

    private val TAG: String = this.javaClass.simpleName

    suspend fun getAllPurchasedProductsCurrentUser(userId: Long, offset: Int) = purchasedProductRepository.getAllPurchasedProductUser(userId, offset)
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addPurchasedProduct(addPurchasedProductItem: AddPurchasedProductItem): NetworkResult<PurchasedProductResponse>{

        Log.wtf(TAG, "ADD PURCHASED PRODUCT")
        if(addPurchasedProductItem.product != null){
            val productId: Long = addPurchasedProductItem.product.id
            val count: Int = addPurchasedProductItem.count.toInt()
            val unitMeasurement: Long = addPurchasedProductItem.unitMeasurement
            val price: Double = addPurchasedProductItem.price.toDouble()
            val purchasedDateTime: Timestamp = Timestamp(Instant.now().epochSecond)
            return purchasedProductRepository.addPurchasedProduct(AddPurchasedProductRequest(
                productId = productId,
                count = count,
                unitMeasurement = unitMeasurement,
                price = price,
                purchasedDateTime = purchasedDateTime)
            )
        }
        else{
            return NetworkResult.Error("продукт не выбран")
        }


    }
}