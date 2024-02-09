package com.mypurchasedproduct.domain.usecases

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import java.sql.Timestamp
import java.time.Instant
import javax.inject.Inject

class PurchasedProductUseCase @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository,
) {

    private val TAG: String = this.javaClass.simpleName

    suspend fun getAllPurchasedProductsCurrentUser(userId: Long, offset: Int) = purchasedProductRepository.getAllPurchasedProductUser(userId, offset)

    suspend fun addPurchasedProduct(
        product: ProductResponse,
        count: String,
        measurementUnitId: Int,
        price: String,
        purchaseDate: Long = Instant.now().epochSecond
    ): NetworkResult<PurchasedProductResponse>{
        Log.wtf(TAG, "ADD PURCHASED PRODUCT")
        if(product != null){
            if(measurementUnitId > 0){
                try{
                    val productId: Long = product.id
                    val count: Int = count.toInt()
                    val unitMeasurement: Long = measurementUnitId.toLong()
                    val price: Double = price.toDouble()

                    return purchasedProductRepository.addPurchasedProduct(
                        AddPurchasedProductRequest(
                            productId = productId,
                            count = count,
                            unitMeasurementId = unitMeasurement,
                            price = price,
                            purchaseDate = purchaseDate
                        )
                    )
                }
                catch (nfe: NumberFormatException){
                    return NetworkResult.Error("есть пустые поля")

                }
            }
            else{
                return NetworkResult.Error("не выбрана еденица измерения")
            }

        }
        else{
            return NetworkResult.Error("продукт не выбран")
        }


    }
}