package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.domain.usecases.ProductUseCase
import com.mypurchasedproduct.presentation.state.AddProductState
import com.mypurchasedproduct.presentation.state.FindProductsState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
): ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    var getProductsState by mutableStateOf(FindProductsState())
        private set

    var addProductState by mutableStateOf(AddProductState())
        private set


    fun getProducts(){
        viewModelScope.launch {
            Log.wtf(TAG, "GET PRODUCTS")
            getProductsState = getProductsState.copy(
                isLoading = true
            )
            val productsResponse = this.async{productUseCase.getProducts()}.await()
            when(productsResponse){
                is NetworkResult.Success ->{
                    getProductsState = getProductsState.copy(
                        isLoading = false,
                        isSuccess = true,
                        isUpdating = false,
                        products = productsResponse.data
                    )
                }
                is NetworkResult.Error -> {
                    getProductsState = getProductsState.copy(
                        isLoading = false,
                        isError = true,
                        isUpdating = false,
                        error = productsResponse.message
                    )
                }
            }
        }
    }
    fun onClickAddProduct(){
        Log.i(TAG, "ON CLICK ADD PRODUCT")
        addProductState = addProductState.copy(
            isActive = true
        )
    }

    fun onClickCloseAddProduct(){
        Log.i(TAG, "ON CLICK CLOSE ADD PRODUCT")
        addProductState = addProductState.copy(
            isActive = false
        )
    }
}