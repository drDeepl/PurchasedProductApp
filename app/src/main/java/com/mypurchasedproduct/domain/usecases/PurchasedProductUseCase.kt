package com.mypurchasedproduct.domain.usecases

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.mypurchasedproduct.data.remote.model.request.AddPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.request.EditPurchasedProductRequest
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import com.mypurchasedproduct.domain.model.AddPurchasedProductModel
import com.mypurchasedproduct.domain.model.EditPurchasedProductModel
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
        addPurchasedProductModel: AddPurchasedProductModel,
        purchaseDate: Long = Instant.now().epochSecond
    ): NetworkResult<PurchasedProductResponse>{
        Log.wtf(TAG, "ADD PURCHASED PRODUCT")
        if(addPurchasedProductModel.product != null && addPurchasedProductModel.measurementUnit != null){
            try{
                val productId: Long = addPurchasedProductModel.product.id
                val count: Int = addPurchasedProductModel.count.toInt()
                val unitMeasurement: Long = addPurchasedProductModel.measurementUnit.id
                val price: Double = addPurchasedProductModel.price.toDouble()

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
                return NetworkResult.Error("есть незаполненные поля")
            }
        }
        else{
            return NetworkResult.Error("продукт не выбран")
        }
    }

    suspend fun editPurchasedProduct(
        editPurchasedProductModel: EditPurchasedProductModel,
    ): NetworkResult<PurchasedProductResponse>{
        Log.wtf(TAG, "ADD PURCHASED PRODUCT")
        if(editPurchasedProductModel.product != null){
            if(editPurchasedProductModel.unitMeasurement > 0){
                try{
                    val productId: Long = editPurchasedProductModel.product.id
                    val count: Int = editPurchasedProductModel.count.toInt()
                    val unitMeasurement: Long = editPurchasedProductModel.unitMeasurement
                    val price: Double = editPurchasedProductModel.price.toDouble()
                    val purchaseDate: Long = Instant.parse(editPurchasedProductModel.purchaseDate).epochSecond

                    return purchasedProductRepository.editPurchasedProduct(
                        editPurchasedProductModel.id,
                        EditPurchasedProductRequest(
                            productId = productId,
                            count = count,
                            unitMeasurementId = unitMeasurement,
                            price = price,
                            userId = editPurchasedProductModel.userId,
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