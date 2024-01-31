package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
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
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    init {}

    fun onAddPurchasedProductClick(){
        addPurchasedProductState = addPurchasedProductState.copy(
            isActive = true
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





    fun onClickSavePurchasedProduct(){
        Log.wtf(TAG, "ON CLICK SAVE PURCHASED PRODUCT")
        Log.i(TAG, "Product: id: \n\t${addPurchasedProductFormData.product?.id}\n" +
                "\t${addPurchasedProductFormData.product?.name}\n" +
                "count: ${addPurchasedProductFormData.count}\n" +
                "unit measurement: ${addPurchasedProductFormData.unitMeasurement}\n" +
                "price: ${addPurchasedProductFormData.price}")
        if(addPurchasedProductFormData.product != null){
            viewModelScope.launch {
                addPurchasedProductState = addPurchasedProductState.copy(
                    isLoading = true
                )
                purchasedProductUseCase.addPurchasedProduct(addPurchasedProductFormData)
            }
        }


    }

    fun onCloseAddPurchasedproduct(){
        addPurchasedProductState = addPurchasedProductState.copy(
            isActive = false
        )
    }
}