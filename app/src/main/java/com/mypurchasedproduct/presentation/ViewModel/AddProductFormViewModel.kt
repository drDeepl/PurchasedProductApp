package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.domain.usecases.ProductUseCase
import com.mypurchasedproduct.presentation.state.AddProductFormState
import com.mypurchasedproduct.presentation.ui.item.ProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductFormViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
): ViewModel() {
    private val TAG: String = this.javaClass.simpleName


    private val _formState = MutableStateFlow(
        AddProductFormState(isActive=false,isLoading=false,isSuccess=false,isError=false)
    )
    val formState = _formState.asStateFlow()

    private val _formData = MutableStateFlow(ProductItem())
    val formData = _formData.asStateFlow()

    private val _errors = MutableStateFlow<MutableList<String>>(mutableListOf())
    val errors = _errors.asStateFlow()

    fun setProductName(name: String){
        viewModelScope.launch {
            Log.w(TAG, "SET PRODUCT NAME")
            _formData.update { formData ->
                formData.copy(
                    productName = name
                )
            }
        }
    }

    fun validate(): MutableList<String> {
            val errors = mutableListOf<String>()
            if(formData.value.productName.isEmpty()){
                errors.add("название продукта не может быть пустым")
            }
            if(formData.value.category.id <= 0){
                errors.add("не выбрана категория для продукта")
            }
        return errors
    }

    fun setErrors(errors: MutableList<String>){
        viewModelScope.launch {
            Log.d(TAG, "SET ERRORS")
            _errors.update { errors }
            _formState.update { formState ->
                formState.copy(
                    isError = true
                )
            }
        }
    }
    fun clearErrors(){
        viewModelScope.launch {
            Log.d(TAG, "CLEAR ERRORS")
            _errors.update { mutableListOf<String>() }
            _formState.update { formState ->
                formState.copy(
                    isError = false
                )
            }
        }
    }

    // TODO: REFACTOR
    fun toAddProduct(productName: String){
        Log.i(TAG,"ADD PRODUCT REQUEST")
        viewModelScope.launch {

            _formState.update { formState ->
                formState.copy(isLoading = true)
            }
            productUseCase.addProduct(formData.value).let {networkResult ->
                when(networkResult){
                    is NetworkResult.Success ->{
                        _formState.update { formState ->
                            formState.copy(
                                isLoading = false,
                                isSuccess = true,
                            )
                        }
                    }
                    is NetworkResult.Error ->{
                        _formState.update { formState ->
                            formState.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                        errors.value.add(networkResult.message.toString())
                    }
                }
            }
        }
    }


    fun setDefaultState(){
        viewModelScope.launch {
            Log.wtf(TAG, "SET DEFAULT ADD PRODUCT STATE")
            _formData.update { ProductItem() }
            _formState.update { AddProductFormState(false,false,false,false) }
            _errors.update { mutableListOf() }
        }
    }


    fun setProductCategory(category: CategoryResponse){
        viewModelScope.launch {
            Log.d(TAG, "SET PRODUCT CATEGORY ID")
            _formData.update { formData ->
                formData.copy(
                    category = category
                )
            }
        }

    }
    fun setActiveForm(isActive: Boolean){
        _formState.update { formState ->
            formState.copy(isActive=isActive)
        }
    }

    fun setLoading(isLoading: Boolean){
        _formState.update { formState ->
            formState.copy(
                isLoading = isLoading
            )
        }
    }
}