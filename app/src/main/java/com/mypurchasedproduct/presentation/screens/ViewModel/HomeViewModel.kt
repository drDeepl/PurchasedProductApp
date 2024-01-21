package com.mypurchasedproduct.presentation.screens.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.local.DataStoreManager
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.presentation.state.FindPurchasedProductsState
import com.mypurchasedproduct.presentation.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val purchasedProductUseCase: PurchasedProductUseCase,
    private val dataStoreManager: DataStoreManager
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    var userState by mutableStateOf(UserState())
        private set

    var getPurchasedProductsState by mutableStateOf(FindPurchasedProductsState())
        private set

    var accessToken by mutableStateOf("")
        private set

    init{
        viewModelScope.launch {
            dataStoreManager.getAccessToken().collect{
                it?. let {accessToken ->
                    userState.copy(
                        accessToken=accessToken,
                        isLoading = false)

                }


            }

        }
    }

}