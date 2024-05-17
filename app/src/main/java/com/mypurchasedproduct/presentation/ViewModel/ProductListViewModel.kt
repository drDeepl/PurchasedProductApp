package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.domain.usecases.ProductUseCase
import com.mypurchasedproduct.presentation.state.ProductListState
import com.mypurchasedproduct.presentation.ui.item.ProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,

): ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    private val _state = MutableStateFlow(ProductListState())
    val state = _state.asStateFlow()

    private val _errors = MutableStateFlow<MutableList<String>>(mutableListOf())
    val errors = _errors.asStateFlow()

    private val _products = MutableStateFlow<MutableList<ProductResponse>>(mutableListOf())
    val products = _products.asStateFlow()


    init{
        viewModelScope.launch {
            Log.d(TAG, "INITIALIZER PRODUCT LIST")
            findProducts()
        }
    }

    private fun findProducts(){
        viewModelScope.launch {
            Log.wtf(TAG, "GET PRODUCTS")
            _state.update { state ->
                state.copy(isLoading = true)
            }
            val networkResult = productUseCase.getProducts()
            when(networkResult){
                is NetworkResult.Success ->{
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isSuccess = true,
                        )
                    }
                    _products.update {  networkResult.data ?: mutableListOf()  }
                }
                is NetworkResult.Error -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                        )
                    }
                    errors.value.add(networkResult.message.toString())
                }
            }
        }
    }

    public fun getLastProduct(): ProductResponse {
        Log.d(TAG, "GET LAST PRODUCT")
        return _products.value.last()
    }


    fun toAddProduct(
        productItem: ProductItem,
        onSuccess: (header: String)-> Unit,
        onError: (header: String, errors: MutableList<String>) -> Unit
    ){
        Log.i(TAG,"ADD PRODUCT REQUEST")
        viewModelScope.launch {

            _state.update { formState ->
                formState.copy(isLoading = true)
            }
            productUseCase.addProduct(productItem).let {networkResult ->
                when(networkResult){
                    is NetworkResult.Success ->{
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                isSuccess = true,
                            )
                        }
                        networkResult.data?.let{product ->
//                            _products.value.add(0, product)
                            _products.value.add(product)

                        }
                        onSuccess("Продукт успешно добавлен!")

                    }
                    is NetworkResult.Error ->{
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                        onError("что-то пошло не так", mutableListOf<String>(networkResult.message.toString()))
                    }
                }
            }
        }
    }
}
