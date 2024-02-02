package com.mypurchasedproduct.presentation.screens.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.presentation.state.DeletePurchasedProductState
import com.mypurchasedproduct.presentation.state.FindPurchasedProductsState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasedProductListViewModel @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository,
): ViewModel(){

    private val TAG: String = this.javaClass.simpleName

    var deletePurchasedProductState by mutableStateOf(DeletePurchasedProductState())
        private set

    var getPurchasedProductsByDateState by mutableStateOf(FindPurchasedProductsState())
        private set



    @RequiresApi(Build.VERSION_CODES.N)
    fun getPurchasedProductCurrentUserByDate(offset: Int){
        getPurchasedProductsByDateState = getPurchasedProductsByDateState.copy(
            isLoading = true
        )
        viewModelScope.launch{
            Log.wtf(TAG, "GET PURCHASED PRODUCT CURRENT USER")
                val purchasedProducts = this.async {
                    purchasedProductRepository.getPurchasedProductsByDate(System.currentTimeMillis()) }
                    .await()
                when(purchasedProducts){
                    is NetworkResult.Success -> {
                        purchasedProducts.data?.let{
                            getPurchasedProductsByDateState = getPurchasedProductsByDateState.copy(
                                isActive = false,
                                purchasedProducts = it,
                                isSuccessResponse = true,
                                isLoading = false,
                            )
                        }

                    }
                    is NetworkResult.Error ->{
                        getPurchasedProductsByDateState = getPurchasedProductsByDateState.copy(
                            isActive = false,
                            error = purchasedProducts.message,
                            isLoading = false
                        )
                    }
                }
            }
        }


    fun onSwipeDelete(purchasedProduct: PurchasedProductResponse){
        Log.wtf(TAG, "ON SWIPE DELETE PURCHASED PRODUCT")
        deletePurchasedProductState = deletePurchasedProductState.copy(
            isActive = true,
            purchasedProduct = purchasedProduct
        )
    }

    fun onDismissDeletePurchasedProduct(){
            Log.wtf(TAG, "ON DISMISS DELETE PURCHASED PRODUCT")
            deletePurchasedProductState = deletePurchasedProductState.copy(
                isActive = false,
                purchasedProduct = null
            )
    }

    fun setDefaultDeletePurchasedProductState(){
        Log.wtf(TAG, "SET DEFAULT DELETE PURCHASED PRODUCT STATE")
        deletePurchasedProductState = DeletePurchasedProductState()
    }

    fun deletePurchasedProduct(){
        Log.wtf(TAG, "DELETE PURCHASED PRODUCT")
        deletePurchasedProductState = deletePurchasedProductState.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val purchasedProduct: PurchasedProductResponse? = deletePurchasedProductState.purchasedProduct
            if( purchasedProduct != null){
                val networkResult: NetworkResult<MessageResponse> = this.async { purchasedProductRepository.deletePurchasedProduct(purchasedProduct.id)}.await()
                when(networkResult){
                    is NetworkResult.Success ->{
                        deletePurchasedProductState = deletePurchasedProductState.copy(
                            isSuccess = true,
                            isLoading = false
                        )
                    }
                    is NetworkResult.Error ->{
                        deletePurchasedProductState = deletePurchasedProductState.copy(isLoading = false,isError = true,error = networkResult.message)
                    }
                }
            }else{
                deletePurchasedProductState = deletePurchasedProductState.copy(isLoading = false,isError = true,error = "что-то пошло не так")
            }
        }
    }
}