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
class AddPurchasedProductFormViewModel @Inject constructor(
    private val purchasedProductUseCase: PurchasedProductUseCase,
    private val measurementUnitUseCase: MeasurementUnitUseCase
) : ViewModel(){
    private val TAG = this.javaClass.simpleName

    private val _state = MutableStateFlow(AddPurchasedProductState())
    val state = _state.asStateFlow()


    var addPurchasedProductState by mutableStateOf(AddPurchasedProductState())
        private set

    var addPurchasedProductFormData by mutableStateOf( AddPurchasedProductItem())
        private set

    var productsBottomSheetState by mutableStateOf(ProductBottomSheetState())

    var findMeasurementUnits by mutableStateOf(FindMeasurementUnitsState())
        private set

    private var  measurementUnits = mutableStateListOf<MeasurementUnitResponse>()




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
            addPurchasedProductState = addPurchasedProductState.copy(
                isActive = isActive
            )
        }


    }
}



//    fun toAddPurchasedProduct(addPurchasedProductModel: AddPurchasedProductModel){
////        product: ProductResponse, count: String, price: String, measurementUnitId: Int
//        Log.wtf(TAG, "TO ADD PURCHASED PRODUCT")
//        viewModelScope.launch {
//            addPurchasedProductState = addPurchasedProductState.copy(
//                isLoading = true
//            )
//            val networkResult =  purchasedProductUseCase.addPurchasedProduct(addPurchasedProductModel)
//            when(networkResult){
//                is NetworkResult.Success ->{
//                    addPurchasedProductState = addPurchasedProductState.copy(
//                        isLoading = false,
//                        isSuccess = true,
//                        product = networkResult.data
//                    )
//                }
//
//                is NetworkResult.Error ->{
//                    addPurchasedProductState = addPurchasedProductState.copy(
//                        isLoading = false,
//                        isError = true,
//                        error = networkResult.message
//                    )
//                }
//            }
//        }
//
//    }

//    fun findMeasurementUnits(){
//        viewModelScope.launch {
//            Log.wtf(TAG, "GET MEASUREMENT UNITS")
//            findMeasurementUnits = findMeasurementUnits.copy(
//                isLoading = true
//            )
//            val measurementUnitsResponse = measurementUnitUseCase.getMeasurementUnits()
//            when(measurementUnitsResponse){
//                is NetworkResult.Success ->{
//                    measurementUnitsResponse.data?.let {
//                        if(measurementUnits.size != it.size){
//                            measurementUnits.addAll(it)
//                        }
//                    }
//                    findMeasurementUnits = findMeasurementUnits.copy(
//                        isLoading = false,
//                        isSuccess = true,
//                        isUpdating = false,
//                    )
//
//                }
//                is NetworkResult.Error -> {
//                    findMeasurementUnits = findMeasurementUnits.copy(
//                        isLoading = false,
//                        isError = true,
//                        isUpdating = false,
//                        error = measurementUnitsResponse.message
//                    )
//                }
//            }
//        }
//    }
//
//    fun getMeasurementUnits(): List<MeasurementUnitResponse>{
//        Log.wtf(TAG, "GET MEASUREMENT UNITS")
//        return measurementUnits
//    }
//}