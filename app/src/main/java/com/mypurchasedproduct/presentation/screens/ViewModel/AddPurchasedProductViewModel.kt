package com.mypurchasedproduct.presentation.screens.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.presentation.state.AddPurchasedProductState
import com.mypurchasedproduct.presentation.state.ProductBottomSheetState
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPurchasedProductViewModel @Inject constructor(
    private val purchasedProductUseCase: PurchasedProductUseCase
) : ViewModel(){
    private val TAG = this.javaClass.simpleName
    var addPurchasedProductState by mutableStateOf(AddPurchasedProductState())
        private set

    var addPurchasedProductFormData by mutableStateOf( AddPurchasedProductItem())
        private set

    var productsBottomSheetState by mutableStateOf(ProductBottomSheetState())

    var measurementUnitBottomSheetState by mutableStateOf(false)

    fun onAddPurchasedProductClick(){
        addPurchasedProductState = addPurchasedProductState.copy(
            isActive = true
        )
    }

    fun setActiveAddPurchasedProductForm(isActive: Boolean){
        Log.wtf(TAG, "SET ACTIVE ADD PURCHASED PRODUCT")
        addPurchasedProductState = addPurchasedProductState.copy(
            isActive = isActive
        )

    }

    fun setOpenProductsBottomSheet(isActive: Boolean){
        productsBottomSheetState = productsBottomSheetState.copy(
            isActive = isActive
        )
    }

    fun setCountFormData(count: String){
        addPurchasedProductFormData = addPurchasedProductFormData.copy(
            count = count
        )
    }
    fun setPriceFormData(price: String){
        addPurchasedProductFormData = addPurchasedProductFormData.copy(
            price = price
        )
    }

    fun setProductFormData(product: ProductResponse){
        addPurchasedProductFormData = addPurchasedProductFormData.copy(
            product = product
        )

    }

    fun setMeasurementUnitId(id: Long){
        addPurchasedProductFormData = addPurchasedProductFormData.copy(
            unitMeasurement = id
        )

    }

    fun setDefaultAddPurchasedProductState(){
        addPurchasedProductState = AddPurchasedProductState(isActive = true)
    }

    fun setDefaultFormDataAddPurchasedProduct(){
        addPurchasedProductFormData = AddPurchasedProductItem()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClickSavePurchasedProduct(){
        Log.wtf(TAG, "ON CLICK SAVE PURCHASED PRODUCT")
        Log.i(TAG, "Product: id: \n\t${addPurchasedProductFormData.product?.id}\n" +
                "\t${addPurchasedProductFormData.product?.name}\n" +
                "count: ${addPurchasedProductFormData.count}\n" +
                "unit measurement: ${addPurchasedProductFormData.unitMeasurement}\n" +
                "price: ${addPurchasedProductFormData.price}")
        viewModelScope.launch {
            addPurchasedProductState = addPurchasedProductState.copy(
                isLoading = true
            )
            val networkResult = this.async { purchasedProductUseCase.addPurchasedProduct(addPurchasedProductFormData)}.await()
            when(networkResult){
                is NetworkResult.Success ->{
                    addPurchasedProductState = addPurchasedProductState.copy(
                        isLoading = false,
                        isSuccess = true,
                        product = networkResult.data
                    )
                }

                is NetworkResult.Error ->{
                    addPurchasedProductState = addPurchasedProductState.copy(
                        isLoading = false,
                        isError = true,
                        error = networkResult.message
                    )
                }
            }
        }
    }

    fun onCloseAddPurchasedproduct(){
        addPurchasedProductState = addPurchasedProductState.copy(
            isActive = false
        )
    }
}