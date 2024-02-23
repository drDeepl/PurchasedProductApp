package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.DoubleAccumulator
import java.util.function.DoubleBinaryOperator
import javax.inject.Inject

@HiltViewModel
class PurchasedProductListViewModel @Inject constructor(
    private val purchasedProductUseCase:PurchasedProductUseCase,
    private val purchasedProductRepository: PurchasedProductRepository,
): ViewModel(){

    private val TAG: String = this.javaClass.simpleName


    private var _state = MutableStateFlow(PurchasedProductsListState(false, true, false, ""))

    var state = _state.asStateFlow()

    private var _purchasedProducts = MutableStateFlow<MutableList<PurchasedProductResponse>>(mutableListOf())
    var purchasedProducts = _purchasedProducts.asStateFlow()

    private val _deletePurchasedProductState = MutableStateFlow(DeletePurchasedProductState())
    val deletePurchasedProductState = _deletePurchasedProductState.asStateFlow()



//    var deletePurchasedProductState by mutableStateOf(DeletePurchasedProductState())
//        private set



    private val _editPurchasedProductState = MutableStateFlow(EditPurchasedProductState())
    val editPurchasedProductState = _editPurchasedProductState.asStateFlow()



    private val _totalCosts = MutableStateFlow(DoubleAccumulator(DoubleBinaryOperator { left, right ->  left + right},(0.0)))
    val totalCosts = _totalCosts.asStateFlow()





    fun toAddPurchasedProduct(
        addPurchasedProductModel: AddPurchasedProductModel,
        timestamp:Long,
        onSuccess: (header:String) -> Unit,
        ){
        viewModelScope.launch {
            _state.update { state ->
                state.copy(isLoading = true)
            }
            Log.d(TAG, "TO ADD PRODUCT")
            val result = purchasedProductUseCase.addPurchasedProduct(addPurchasedProductModel, timestamp)
            when(result){
                is NetworkResult.Success ->{
                    result.data?.let {
                        _purchasedProducts.value.add(it)
                        _totalCosts.value.accumulate(it.price)
                    }
                    onSuccess("запись добавлена!")
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
            _state.update{ purchasedProductsListState ->
                purchasedProductsListState.copy(
                    isLoading = true,
                )
            }
            val networkResult = purchasedProductRepository.getPurchasedProductsByDate(timestamp)
            when(networkResult){
                is NetworkResult.Success ->{
                    _purchasedProducts.value = networkResult.data?.toMutableList() ?: mutableListOf()
                    _state.update{ purchasedProductsListState ->
                        purchasedProductsListState.copy(
                            isLoading = false,
                            isUpdate = false
                        )
                    }
                    calculateTotalCosts(networkResult.data ?: listOf())
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
        viewModelScope.launch {
            Log.wtf(TAG, "ON SWIPE DELETE PURCHASED PRODUCT")
            _deletePurchasedProductState.update {deletePurchasedProductState ->
                deletePurchasedProductState.copy(
                    isActive = true,
                    purchasedProduct = purchasedProduct
                )
            }

        }
    }

//    fun onSwipeEditPurchasedProduct(purchasedProduct: PurchasedProductResponse){
//        viewModelScope.launch {
//            Log.wtf(TAG, "ON SWIPE EDIT PURCHASED PRODUCT")
//            _editPurchasedProductState.update { editPurchasedProductState ->
//                editPurchasedProductState.copy(
//                    isActive = true,
//                    purchasedProduct = purchasedProduct
//                )
//            }
//
//        }
//    }

    fun setDefaultEditPurchasedProductState(){
        viewModelScope.launch {
            Log.wtf(TAG, "SET DEFAULT EDIT PURCHASED PRODUCT STATE")
            _editPurchasedProductState.update { EditPurchasedProductState()
            }
        }
    }

    fun toEditPurchasedProduct(editPurchasedProductModel: EditPurchasedProductModel, onError: (String) -> Unit){
        Log.wtf(TAG, "TO EDIT PURCHASED PRODUCT MODEL")
        viewModelScope.launch {
            _editPurchasedProductState.update { editPurchasedProductState ->
                editPurchasedProductState.copy(
                    isLoading = true
                )
            }
            val result = purchasedProductUseCase.editPurchasedProduct(editPurchasedProductModel)
            when(result){
                is NetworkResult.Success ->{
                    // TODO: ADD PurchasedProductResponse to array
                    result.data?.let {purchasedProductResponse ->
                        val index: Int = _purchasedProducts.value.indexOfFirst {purchasedProduct ->
                            purchasedProduct.id == purchasedProductResponse.id
                        }
                        _purchasedProducts.value[index]= purchasedProductResponse

                    }
                    _editPurchasedProductState.update { editPurchasedProductState ->
                        editPurchasedProductState.copy(
                            isSuccess = true,
                            isLoading = false,
                            isActive = false
                        )
                    }

                }
                is NetworkResult.Error ->{
                    _editPurchasedProductState.update { editPurchasedProductState ->
                        editPurchasedProductState.copy(
                            isError = true,
                            isLoading = false,
                        )
                    }
                    onError(result.message.toString())
                }
            }
        }
    }



    fun onDismissDeletePurchasedProduct(){
        viewModelScope.launch {
            Log.wtf(TAG, "ON DISMISS DELETE PURCHASED PRODUCT")
            _deletePurchasedProductState.update {deletePurchasedProductState ->
                deletePurchasedProductState.copy(
                    isActive = false,
                    purchasedProduct = null
                )
            }

        }
    }

    fun setDefaultDeletePurchasedProductState(){
        viewModelScope.launch {
            Log.wtf(TAG, "SET DEFAULT DELETE PURCHASED PRODUCT STATE")
            _deletePurchasedProductState.value = DeletePurchasedProductState()

        }
    }


    fun calculateTotalCosts(purchasedProducts: List<PurchasedProductResponse>){
        Log.wtf(TAG, "CALCULATE TOTAL COSTS")
        viewModelScope.launch {
            _totalCosts.value = DoubleAccumulator(DoubleBinaryOperator { left, right ->  left + right},(0.0))
            purchasedProducts.forEach{
                _totalCosts.value.accumulate(it.price)
            }
        }
    }

    fun deletePurchasedProduct(){
        viewModelScope.launch {
            Log.wtf(TAG, "ON DISMISS DELETE PURCHASED PRODUCT")
            _deletePurchasedProductState.update {deletePurchasedProductState ->
                deletePurchasedProductState.copy(
                    isLoading = true
                )
            }
            val purchasedProduct: PurchasedProductResponse? = deletePurchasedProductState.value.purchasedProduct
            if( purchasedProduct != null){
                val networkResult = purchasedProductRepository.deletePurchasedProduct(purchasedProduct.id)
                when(networkResult){
                    is NetworkResult.Success ->{
                        _deletePurchasedProductState.update {deletePurchasedProductState ->
                            deletePurchasedProductState.copy(
                                isSuccess = true,
                                isLoading = false
                            )
                        }

                        deletePurchasedProductState.value.purchasedProduct?.let {purchasedProduct ->
//                            _purchasedProducts.update { purchasedProducts ->
//                                purchasedProducts.filter { it.id != purchasedProduct.id  }
//                            }
                            _purchasedProducts.value.removeIf{
                                it.id == purchasedProduct.id
                            }
                            _totalCosts.value.accumulate(-purchasedProduct.price)

                        }



                    }
                    is NetworkResult.Error ->{
                        _deletePurchasedProductState.update {deletePurchasedProductState ->
                            deletePurchasedProductState.copy(
                                isLoading = false,
                                isError = true,
                                error = networkResult.message
                            )
                        }
                    }
                }
            }else{
                _deletePurchasedProductState.update {deletePurchasedProductState ->
                    deletePurchasedProductState.copy(
                        isLoading = false,
                        isError = true,
                        error = "что-то пошло не так"
                    )
                }

            }
        }
    }
}