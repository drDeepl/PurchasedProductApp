package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.domain.usecases.ProductUseCase
import com.mypurchasedproduct.presentation.state.AddProductFormState
import com.mypurchasedproduct.presentation.state.AddProductState
import com.mypurchasedproduct.presentation.state.FindProductsState
import com.mypurchasedproduct.presentation.state.GetCategoriesState
import com.mypurchasedproduct.presentation.ui.item.ProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
): ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    var getProductsState by mutableStateOf(FindProductsState())
        private set

    var addProductState by mutableStateOf(AddProductState())
        private set

    var addProductFormState by mutableStateOf(AddProductFormState())

    var productItem by mutableStateOf(ProductItem())
        private set

    var getCategoriesState by mutableStateOf(GetCategoriesState())
        private set


    fun getCategories(){
        viewModelScope.launch {
            Log.wtf(TAG, "GET CATEGORIES")
            getCategoriesState = getCategoriesState.copy(
                isLoading = true
            )
            productUseCase.getCategories().let{networkResult ->

                when(networkResult){
                    is NetworkResult.Success ->{
                        getCategoriesState = getCategoriesState.copy(
                            isLoading = false,
                            categories = networkResult.data
                        )
                    }
                    is NetworkResult.Error ->{
                        getCategoriesState = getCategoriesState.copy(
                            isLoading = false,
                            isError = true,
                            error = networkResult.message
                        )
                    }
                }

            }
        }
    }


    fun getProducts(){
        viewModelScope.launch {
            Log.wtf(TAG, "GET PRODUCTS")
            getProductsState = getProductsState.copy(
                isLoading = true
            )
            val productsResponse = this.async{productUseCase.getProducts()}.await()
            when(productsResponse){
                is NetworkResult.Success ->{
                    getProductsState = getProductsState.copy(
                        isLoading = false,
                        isSuccess = true,
                        isUpdating = false,
                        products = productsResponse.data
                    )
                }
                is NetworkResult.Error -> {
                    getProductsState = getProductsState.copy(
                        isLoading = false,
                        isError = true,
                        isUpdating = false,
                        error = productsResponse.message
                    )
                }
            }
        }
    }

    fun toAddProduct(){
//        TODO("FIXED API ERROR")
        Log.i(TAG,"ADD PRODUCT REQUEST")
        viewModelScope.launch {
            addProductState = addProductState.copy(
                isLoading = true
            )
            productUseCase.addProduct(productItem).let {networkResult ->
                when(networkResult){
                    is NetworkResult.Success ->{
                        addProductState = addProductState.copy(
                            isLoading = false,
                            isSuccess = true,
                            product = networkResult.data,
                            msg = "Добавил продукт!"
                        )
                    }
                    is NetworkResult.Error ->{
                        addProductState = addProductState.copy(
                            isLoading = false,
                            isError = true,
                            error = networkResult.message
                        )
                    }
                }
            }
        }
    }

    fun setDefaultStateAddProduct(){
        addProductState = AddProductState()
    }

    fun setProductName(name: String){
        productItem = productItem.copy(
            productName = name
        )
    }

    fun setProductCategoryId(categoryId: Long){
        productItem = productItem.copy(
            categoryId = categoryId
        )
    }

    fun onClickAddProduct(){
        Log.i(TAG, "ON CLICK ADD PRODUCT")
        addProductFormState = addProductFormState.copy(
            isActive = true
        )
    }

    fun onClickCloseAddProduct(){
        Log.i(TAG, "ON CLICK CLOSE ADD PRODUCT")
        addProductFormState = addProductFormState.copy(
            isActive = false
        )
    }
}