package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import com.mypurchasedproduct.domain.model.AddPurchasedProductModel
import com.mypurchasedproduct.domain.model.EditPurchasedProductModel
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.presentation.state.DeletePurchasedProductState
import com.mypurchasedproduct.presentation.state.EditPurchasedProductState
import com.mypurchasedproduct.presentation.state.PurchasedProductsListState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.joda.time.Instant
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class PurchasedProductListViewModel @Inject constructor(
    private val purchasedProductUseCase:PurchasedProductUseCase,
    private val purchasedProductRepository: PurchasedProductRepository,
): ViewModel(){

    private val TAG: String = this.javaClass.simpleName


    private var _state = MutableStateFlow(PurchasedProductsListState(false, true, false, ""))

    var state = _state.asStateFlow()

    private var _pruchasedProducts = MutableStateFlow<List<PurchasedProductResponse>>(listOf())
    var purchasedProducts = _pruchasedProducts.asStateFlow()



    var deletePurchasedProductState by mutableStateOf(DeletePurchasedProductState())
        private set

    var editPurchasedProductState by mutableStateOf(EditPurchasedProductState())



    var totalCosts by mutableStateOf(AtomicInteger(0))
        private set




    fun toAddPurchasedProduct(addPurchasedProductModel: AddPurchasedProductModel, timestamp:Long){
        viewModelScope.launch {
            _state.update { state ->
                state.copy(isLoading = true)
            }
            Log.d(TAG, "TO ADD PRODUCT")
            val result = purchasedProductUseCase.addPurchasedProduct(addPurchasedProductModel, timestamp)
            when(result){
                is NetworkResult.Success ->{
                    result.data?.let {
                        _pruchasedProducts.update { purchasedProducts ->
                            purchasedProducts.plus(it)
                        }
                    }
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
    fun getPurchasedProductCurrentUserByDate(timestamp: Long){
        viewModelScope.launch{
            Log.wtf(TAG, "GET PURCHASED PRODUCT CURRENT USER")
            _state.update{ purchasedProductsListState ->
                purchasedProductsListState.copy(
                    isLoading = true,
                )
            }
            val networkResult = purchasedProductRepository.getPurchasedProductsByDate(timestamp)
            when(networkResult){
                is NetworkResult.Success ->{
                    _pruchasedProducts.update { purchasedProducts ->
                        purchasedProducts
                    }
                    _state.update{ purchasedProductsListState ->
                        purchasedProductsListState.copy(
                            isLoading = false,
                            isUpdate = false
                        )
                    }
                }

                is NetworkResult.Error ->{
                    _state.update{ purchasedProductsListState ->
                        purchasedProductsListState.copy(
                            isLoading = false,
                            isUpdate = false,
                            isError = true,
                            error = networkResult.message.toString()

                        )
                    }

                }
            }
        }
    }


    fun onSwipeDeletePurchasedProduct(purchasedProduct: PurchasedProductResponse){
        Log.wtf(TAG, "ON SWIPE DELETE PURCHASED PRODUCT")
        deletePurchasedProductState = deletePurchasedProductState.copy(
            isActive = true,
            purchasedProduct = purchasedProduct
        )
    }

    fun onSwipeEditPurchasedProduct(purchasedProduct: PurchasedProductResponse){
        Log.wtf(TAG, "ON SWIPE EDIT PURCHASED PRODUCT")
        editPurchasedProductState = editPurchasedProductState.copy(
            isActive = true,
            purchasedProduct = purchasedProduct
        )
    }

    fun toEditPurchasedProduct(editPurchasedProductModel: EditPurchasedProductModel){
        Log.wtf(TAG, "TO EDIT PURCHASED PRODUCT MODEL")
        viewModelScope.launch {
            editPurchasedProductState = editPurchasedProductState.copy(
                isLoading = true
            )
            val response = purchasedProductUseCase.editPurchasedProduct(editPurchasedProductModel)
            when(response){
                is NetworkResult.Success ->{
                    // TODO: ADD PurchasedProductResponse to array
                    editPurchasedProductState = editPurchasedProductState.copy(
                        isSuccess = true,
                        isLoading = false,
                        isActive = false
                    )
                }
                is NetworkResult.Error ->{
                    editPurchasedProductState = editPurchasedProductState.copy(
                        isError = true,
                        isLoading = false,
                        error = response.message
                    )
                }
            }
        }
    }

    fun setActiveEditPurchasedProduct(isActive: Boolean){
        editPurchasedProductState = editPurchasedProductState.copy(
            isActive = isActive,
        )

    }

    fun setDefaultEditPurchasedProductState(){
        editPurchasedProductState = EditPurchasedProductState()
    }


    fun onDismissDeletePurchasedProduct(){
            Log.wtf(TAG, "ON DISMISS DELETE PURCHASED PRODUCT")
            deletePurchasedProductState = deletePurchasedProductState.copy(
                isActive = false,
                purchasedProduct = null
            )
    }

    fun setDefaultDeletePurchasedProductState(){
        Log.wtf(TAG, "SET DEFAULT DELETE PURCHASED PRODUCT STATE")
        deletePurchasedProductState = DeletePurchasedProductState()
    }


    fun calculateTotalCosts(purchasedProducts: List<PurchasedProductResponse>){
        Log.wtf(TAG, "CALCULATE TOTAL COSTS")
        totalCosts = AtomicInteger(0)
        viewModelScope.launch {
            purchasedProducts.forEach{
                totalCosts.addAndGet(it.price.toInt())
            }

        }
    }

    fun deletePurchasedProduct(){
        Log.wtf(TAG, "DELETE PURCHASED PRODUCT")
        deletePurchasedProductState = deletePurchasedProductState.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val purchasedProduct: PurchasedProductResponse? = deletePurchasedProductState.purchasedProduct
            if( purchasedProduct != null){
                val networkResult: NetworkResult<MessageResponse> = this.async { purchasedProductRepository.deletePurchasedProduct(purchasedProduct.id)}.await()
                when(networkResult){
                    is NetworkResult.Success ->{
                        deletePurchasedProductState = deletePurchasedProductState.copy(
                            isSuccess = true,
                            isLoading = false
                        )
                    }
                    is NetworkResult.Error ->{
                        deletePurchasedProductState = deletePurchasedProductState.copy(isLoading = false,isError = true,error = networkResult.message)
                    }
                }
            }else{
                deletePurchasedProductState = deletePurchasedProductState.copy(isLoading = false,isError = true,error = "что-то пошло не так")
            }
        }
    }
}