package com.mypurchasedproduct.presentation.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.domain.model.TokenModel
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.domain.usecases.TokenUseCase
import com.mypurchasedproduct.presentation.state.PurchasedProductsListState
import com.mypurchasedproduct.presentation.state.HomeState
import com.mypurchasedproduct.presentation.state.AccessTokenItem
import com.mypurchasedproduct.presentation.state.CheckTokenState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    private val _state = MutableStateFlow(HomeState(isLoading=false, isError = false, error=""))
    val state = _state.asStateFlow()

    init {
        Log.e(TAG, "INIT VIEW MODEL")
    }
    fun setLoadingState(isLoading: Boolean){
        viewModelScope.launch {
            Log.i(TAG, "SET LOADING STATE")
            _state.update { state ->
                state.copy(isLoading = isLoading)
            }
        }
    }
    fun defaultHomeState(){
        viewModelScope.launch{
            _state.update { state ->
                HomeState(false, false, "")
            }
        }
    }
}