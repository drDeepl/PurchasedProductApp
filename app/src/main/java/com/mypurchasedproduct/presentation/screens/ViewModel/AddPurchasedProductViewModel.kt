package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.presentation.state.AddPurchasedProductState
import com.mypurchasedproduct.presentation.state.ProductBottomSheetState
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem
import javax.inject.Inject

class AddPurchasedProductViewModel @Inject constructor() : ViewModel(){
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

    fun setSelectedProductProductsBottomSheet(product: ProductResponse){
        productsBottomSheetState = productsBottomSheetState.copy(
            selectedProduct = product
        )
    }

    fun setProductIdAndCategoryIdByProductObj(product: ProductResponse){
        // TODO("SET PRODUCT ID AND CATEGORY")
        addPurchasedProductFormData = addPurchasedProductFormData.copy(
        )
    }
    fun OnClickSavePurchasedProduct(){
        Log.wtf(TAG, "ON CLICK SAVE PURCHASED PRODUCT")

    }

    fun onCloseAddPurchasedproduct(){
        addPurchasedProductState = addPurchasedProductState.copy(
            isActive = false
        )
    }
}