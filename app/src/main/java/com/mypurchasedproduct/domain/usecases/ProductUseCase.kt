package com.mypurchasedproduct.domain.usecases

import android.net.Network
import android.util.Log
import com.mypurchasedproduct.data.remote.model.request.AddProductRequest
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.repository.ProductRepository
import com.mypurchasedproduct.presentation.ui.item.ProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
){
    private val TAG: String = this.javaClass.simpleName

    suspend fun getProducts(): NetworkResult<MutableList<ProductResponse>> = productRepository.getProducts()

    suspend fun addProduct(productItem: ProductItem): NetworkResult<ProductResponse>{
        Log.wtf(TAG, "ADD PRODUCT\n ${productItem.category.id}\t${productItem.productName}")
        return productRepository.addProduct(
            AddProductRequest(
                categoryId=productItem.category.id,
                name=productItem.productName
            )
        )

//        if(productItem.categoryId != null && productItem.productName.isNotEmpty()) {
//            return productRepository.addProduct(
//                AddProductRequest(
//                    productItem.categoryId,
//                    productItem.productName
//                )
//            )
//        }
//        else{
//            return NetworkResult.Error<ProductResponse>("есть пустые поля или категория не выбрана")
//        }
    }

    suspend fun getCategories(): NetworkResult<MutableList<CategoryResponse>> = productRepository.getCategories()
}