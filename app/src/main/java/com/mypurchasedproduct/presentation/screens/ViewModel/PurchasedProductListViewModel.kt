package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import com.mypurchasedproduct.presentation.state.DeletePurchasedProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasedProductListViewModel @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository
): ViewModel(){

    private val TAG: String = this.javaClass.simpleName

    var deletePurchasedProductState by mutableStateOf(DeletePurchasedProductState())
        private set

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

    fun deletePurchasedProduct(){
        Log.wtf(TAG, "DELETE PURCHASED PRODUCT")
        deletePurchasedProductState = deletePurchasedProductState.copy(
            isLoading = true
        )
        TODO("ADD REQUEST TO DELETE PRODUCT")

        viewModelScope.launch {
            purchasedProductRepository.deletePurchasedProduct()
        }


    }
}