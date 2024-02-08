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
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addPurchasedProduct(product: ProductResponse, count: String, measurementUnitId: Int, price: String): NetworkResult<PurchasedProductResponse>{
        Log.wtf(TAG, "ADD PURCHASED PRODUCT")
        if(product != null){
            if(measurementUnitId > 0){
                try{
                    val productId: Long = product.id
                    val count: Int = count.toInt()
                    val unitMeasurement: Long = measurementUnitId.toLong()
                    val price: Double = price.toDouble()
                    val purchasedDateTime: String = Instant.now().toString()
                    return purchasedProductRepository.addPurchasedProduct(AddPurchasedProductRequest(
                        productId = productId,
                        count = count,
                        unitMeasurement = unitMeasurement,
                        price = price,
                        purchasedDatetime = purchasedDateTime)
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