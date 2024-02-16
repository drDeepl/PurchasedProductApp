package com.mypurchasedproduct.presentation.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.domain.model.AddPurchasedProductModel
import com.mypurchasedproduct.domain.usecases.MeasurementUnitUseCase
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.presentation.state.AddPurchasedProductState
import com.mypurchasedproduct.presentation.state.FindMeasurementUnitsState
import com.mypurchasedproduct.presentation.state.ProductBottomSheetState
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPurchasedProductViewModel @Inject constructor(
    private val purchasedProductUseCase: PurchasedProductUseCase,
    private val measurementUnitUseCase: MeasurementUnitUseCase
) : ViewModel(){
    private val TAG = this.javaClass.simpleName
    var addPurchasedProductState by mutableStateOf(AddPurchasedProductState())
        private set

    var addPurchasedProductFormData by mutableStateOf( AddPurchasedProductItem())
        private set

    var productsBottomSheetState by mutableStateOf(ProductBottomSheetState())

    var findMeasurementUnits by mutableStateOf(FindMeasurementUnitsState())
        private set

    private var  measurementUnits = mutableStateListOf<MeasurementUnitResponse>()




    fun setActiveAddPurchasedProductForm(isActive: Boolean){
        Log.wtf(TAG, "SET ACTIVE ADD PURCHASED PRODUCT")
        viewModelScope.launch {
            addPurchasedProductState = addPurchasedProductState.copy(
                isActive = isActive
            )
        }


    }

    fun setOpenProductsBottomSheet(isActive: Boolean){
        productsBottomSheetState = productsBottomSheetState.copy(
            isActive = isActive
        )
    }

    fun setDefaultAddPurchasedProductState(){
        addPurchasedProductState = AddPurchasedProductState(isActive = true)
    }

    fun setDefaultFormDataAddPurchasedProduct(){
        addPurchasedProductFormData = AddPurchasedProductItem()
    }

    fun toAddPurchasedProduct(addPurchasedProductModel: AddPurchasedProductModel){
//        product: ProductResponse, count: String, price: String, measurementUnitId: Int
        Log.wtf(TAG, "TO ADD PURCHASED PRODUCT")
        viewModelScope.launch {
            addPurchasedProductState = addPurchasedProductState.copy(
                isLoading = true
            )
            val networkResult =  purchasedProductUseCase.addPurchasedProduct(addPurchasedProductModel)
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

    fun findMeasurementUnits(){
        viewModelScope.launch {
            Log.wtf(TAG, "GET MEASUREMENT UNITS")
            findMeasurementUnits = findMeasurementUnits.copy(
                isLoading = true
            )
            val measurementUnitsResponse = measurementUnitUseCase.getMeasurementUnits()
            when(measurementUnitsResponse){
                is NetworkResult.Success ->{
                    measurementUnitsResponse.data?.let {
                        if(measurementUnits.size != it.size){
                            measurementUnits.addAll(it)
                        }
                    }
                    findMeasurementUnits = findMeasurementUnits.copy(
                        isLoading = false,
                        isSuccess = true,
                        isUpdating = false,
                    )

                }
                is NetworkResult.Error -> {
                    findMeasurementUnits = findMeasurementUnits.copy(
                        isLoading = false,
                        isError = true,
                        isUpdating = false,
                        error = measurementUnitsResponse.message
                    )
                }
            }
        }
    }

    fun getMeasurementUnits(): List<MeasurementUnitResponse>{
        Log.wtf(TAG, "GET MEASUREMENT UNITS")
        return measurementUnits
    }
}