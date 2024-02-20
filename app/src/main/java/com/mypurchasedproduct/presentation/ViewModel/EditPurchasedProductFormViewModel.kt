package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.state.EditPurchasedProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditPurchasedProductFormViewModel: ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    private val _state = MutableStateFlow(EditPurchasedProductState())
    val state = _state.asStateFlow()


    fun onEdit(purchasedProduct: PurchasedProductResponse){
        viewModelScope.launch{
            Log.d(TAG, "ON EDIT")
            _state.update { editPurchasedProductState ->
                editPurchasedProductState.copy(
                    isActive = true,
                    purchasedProduct = purchasedProduct
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
}