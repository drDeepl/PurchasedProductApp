package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.R
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddPurchasedProductFormViewModel @Inject constructor() : ViewModel(){

    private val TAG = this.javaClass.simpleName

    private val _state = MutableStateFlow(AddPurchasedProductState())
    val state = _state.asStateFlow()



    fun setSuccessState(){
        viewModelScope.launch {
            Log.d(TAG, "SET SUCCESS")
            _state.update { state ->
                state.copy(isSuccess = true)
            }
        }
    }

    fun setErrorState(msg: String){
        viewModelScope.launch {
            Log.d(TAG, "SET ERROR")
            _state.update { state ->
                state.copy(isError = true)
            }
        }
    }

    fun setDefaultState(){
        viewModelScope.launch {
            Log.d(TAG, "SET DEFAULT STATE")
            _state.value = AddPurchasedProductState()
        }
    }

   fun getAddPurchasedProductModel(): AddPurchasedProductModel{
        return AddPurchasedProductModel(
            product = state.value.product,
            count=state.value.count,
            measurementUnit = state.value.measurementUnit,
            price = state.value.price,
        )
    }
    fun setMeasurementUnit(measurementUnit: MeasurementUnitResponse){
        viewModelScope.launch {
            Log.d(TAG, "SET MEASUREMENT UNIT")
            _state.update { state ->
                state.copy(
                    measurementUnit = measurementUnit
                )
            }
        }
    }
    fun setProduct(product: ProductResponse){
        viewModelScope.launch {
            Log.d(TAG, "SET PRODUCT")
            _state.update { state ->
                state.copy(product = product)
            }
        }
    }

    fun setPrice(price: String){
        viewModelScope.launch {
            Log.d(TAG, "SET PRODUCT")
            _state.update { state ->
                state.copy(price = price)
            }
        }
    }

    fun setCount(count: String){
        viewModelScope.launch {
            Log.d(TAG, "SET COUNT")
            _state.update { state ->
                state.copy(count = count)
            }
        }
    }


    fun setActiveAddPurchasedProductForm(isActive: Boolean){
        Log.wtf(TAG, "SET ACTIVE ADD PURCHASED PRODUCT")
        viewModelScope.launch {
            _state.update{state ->
                state.copy(isActive = isActive)
            }
        }
    }
}
