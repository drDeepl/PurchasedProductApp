package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
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

    var getCategoriesState by mutableStateOf(GetCategoriesState())
        private set

    private var products = mutableStateListOf<ProductResponse>()



    fun findCategories(){
        viewModelScope.launch {
            Log.wtf(TAG, "GET CATEGORIES")
            getCategoriesState = getCategoriesState.copy(
                isLoading = true
            )
            val networkResult = productUseCase.getCategories()
                when(networkResult){
                    is NetworkResult.Success ->{
                        getCategoriesState = getCategoriesState.copy(
                            isLoading = false,
                            isSuccess = true,
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


    fun findProducts(){
        viewModelScope.launch {
            Log.wtf(TAG, "GET PRODUCTS")
            getProductsState = getProductsState.copy(
                isLoading = true
            )
            val productsResponse = productUseCase.getProducts()
            when(productsResponse){
                is NetworkResult.Success ->{
                    getProductsState = getProductsState.copy(
                        isLoading = false,
                        isSuccess = true,
                        isUpdating = false,
                        products = productsResponse.data ?: listOf()
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

    fun getProducts(): List<ProductResponse>{
        Log.wtf(TAG, "GET PRODUCTS")
        return products
    }


    fun toAddProduct(productName: String){
        Log.i(TAG,"ADD PRODUCT REQUEST")
        productItem = productItem.copy(productName = productName)
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


    fun setDefaultAddProductState(){
        Log.wtf(TAG, "SET DEFAULT ADD PRODUCT STATE")
        addProductState = AddProductState()
    }

    fun setDefaultAddProductFormState(){
        addProductFormState = AddProductFormState()
    }

    fun setDefaultProductItem(){
        productItem = ProductItem()
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