package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.repository.ProductRepository
import com.mypurchasedproduct.presentation.state.ProductsListState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListBottomSheetViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    private val _state = MutableStateFlow(ProductsListState(false,false, false, ""))
    val state = _state.asStateFlow()

    private val _products: MutableStateFlow<MutableList<ProductResponse>> = MutableStateFlow(mutableListOf())
    val products = _products.asStateFlow()

    init{
        viewModelScope.launch {
            Log.d(TAG, "INIT")
            findProducts()
        }
    }

    fun setActive(isActive: Boolean){
        viewModelScope.launch {
            Log.d(TAG, "SET ACTIVE")
            _state.update{state ->
                state.copy(
                    isActive = isActive
                )
            }
        }
    }
    fun findProducts(){
        viewModelScope.launch {
            Log.d(TAG, "FIND PRODUCTS")
            _state.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            val result: NetworkResult<MutableList<ProductResponse>> = productRepository.getProducts()
            when(result){
                is NetworkResult.Success ->{
                    _products.update { result.data ?: mutableListOf()}
                    _state.update { state ->
                        state.copy(isLoading = false)
                    }
                }
                is NetworkResult.Error ->{
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            error = result.message.toString())
                    }

                }
            }
        }

    }

}