package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _errors = MutableStateFlow<MutableList<String>>(mutableListOf())
    val errors = _errors.asStateFlow()



    private val _productItem = MutableStateFlow(ProductItem())
    val productItem = _productItem.asStateFlow()



    fun setProductName(name: String){
        viewModelScope.launch {
            Log.w(TAG, "SET PRODUCT NAME")
            _productItem.update { productItem ->
                productItem.copy(
                    productName = name
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
            productUseCase.addProduct(_productItem.value).let {networkResult ->
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
            _formState.update { AddProductFormState(false,false,false,false) }
            _errors.update { mutableListOf() }
        }
    }


    fun setProductCategoryId(categoryId: Long){
        viewModelScope.launch {
            Log.d(TAG, "SET PRODUCT CATEGORY ID")
            _productItem.update { productItem ->
                productItem.copy(
                    categoryId = categoryId
                )
            }
        }

    }

 fun setActiveForm(isActive: Boolean){
     _formState.update { formState ->
         formState.copy(isActive=isActive)
     }
 }
}