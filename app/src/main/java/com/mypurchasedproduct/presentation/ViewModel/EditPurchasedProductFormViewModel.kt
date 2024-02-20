package com.mypurchasedproduct.presentation.ViewModel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.state.EditPurchasedProductState
import com.mypurchasedproduct.presentation.utils.InitModelsResponseUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPurchasedProductFormViewModel @Inject constructor(
) : ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    private val _state = MutableStateFlow(EditPurchasedProductState())
    val state = _state.asStateFlow()
    private val _errors = MutableStateFlow<MutableList<String>>(mutableListOf())
    val errors = _errors.asStateFlow()

    private val _purchasedProductToEdit = MutableStateFlow(
        InitModelsResponseUtils.initPurchasedProductResponse
    )

    val purchasedProductToEdit = _purchasedProductToEdit.asStateFlow()


    fun validate(newProduct: ProductResponse, newCount: String, newMeasurementUnit: MeasurementUnitResponse, newPrice: String): Boolean{
        Log.d(TAG, "VALIDATE")
        val validateErrors: MutableList<String> = mutableListOf()
        if(newProduct.id <= 0){
            validateErrors.add("продукт не найден")
        }
        if(newCount.isNotEmpty() && TextUtils.isDigitsOnly(newCount)){
            validateErrors.add("не заполено поле с количеством")
        }
        if(newMeasurementUnit.id <= 0){
            validateErrors.add("единица измерения не найдена")
        }
        if(newPrice.isNotEmpty() && TextUtils.isDigitsOnly(newPrice)){
            validateErrors.add("не заполено поле с ценой")
        }
        if(validateErrors.size > 0){
            Log.d(TAG, "VALIDATE. ERRORS: SIZE: ${validateErrors.size}")
            _errors.value.addAll(validateErrors)
            _state.update {
                it.copy(
                    isError = true
                )
            }
            return false
        }
        return true
    }

    fun setLoading(isLoading: Boolean){
        _state.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }

    fun setPurchasedProduct(purchasedProduct: PurchasedProductResponse){
        viewModelScope.launch{
            Log.d(TAG, "ON EDIT")
            _purchasedProductToEdit.value = purchasedProduct
        }
    }

    fun setProduct(product: ProductResponse){
        viewModelScope.launch{
            Log.d(TAG, "SET PRODUCT")
            _purchasedProductToEdit.update { purchasedProductToEdit ->
                purchasedProductToEdit.copy(
                    product = product
                )
            }
        }
    }

    fun setCount(count: Int){
        viewModelScope.launch{
            Log.d(TAG, "SET COUNT")
            _purchasedProductToEdit.update { purchasedProductToEdit ->
                purchasedProductToEdit.copy(
                    count = count
                )
            }
        }
    }

    fun setMeasurementUnit(measurementUnitResponse: MeasurementUnitResponse){
        viewModelScope.launch{
            Log.d(TAG, "SET MEASUREMENT UNIT")
            _purchasedProductToEdit.update { purchasedProductToEdit ->
                purchasedProductToEdit.copy(
                    unitMeasurement = measurementUnitResponse
                )
            }
        }
    }

    fun setPrice(price: Double){
        viewModelScope.launch{
            Log.d(TAG, "SET PRICE")
            _purchasedProductToEdit.update { purchasedProductToEdit ->
                purchasedProductToEdit.copy(
                    price = price
                )
            }
        }
    }

    fun setErrorState(isError: Boolean){
        viewModelScope.launch {
            Log.d(TAG, "SET ERROR")
            _state.update { state->
                state.copy(
                    isError = isError,
                )
            }
        }
    }



    fun setActive(isActive: Boolean){
        viewModelScope.launch {
            Log.d(TAG, "SET ACTIVE")
            _state.update { editPurchasedProductState ->
                editPurchasedProductState.copy(isActive = isActive)
            }
        }
    }

    fun setDefaultState(){
        viewModelScope.launch {
            Log.d(TAG, "SET DEFAULT STATE")
            _state.update { EditPurchasedProductState()}
        }
    }

    fun clearErrors(){
        viewModelScope.launch{
            Log.d(TAG, "CLEAR ERRORS")
            _errors.update { mutableListOf() }
        }
    }
}