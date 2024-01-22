package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mypurchasedproduct.data.local.DataStoreManager
import com.mypurchasedproduct.domain.TokenItem
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.domain.usecases.TokenUseCase
import com.mypurchasedproduct.presentation.state.FindPurchasedProductsState
import com.mypurchasedproduct.presentation.state.UserState
import com.mypurchasedproduct.presentation.state.UserTokenState
import com.mypurchasedproduct.presentation.utils.JwtTokenUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val purchasedProductUseCase: PurchasedProductUseCase,
    private val tokenUseCase: TokenUseCase
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    var userTokenState by mutableStateOf(UserTokenState())
        private set

    var getPurchasedProductsState by mutableStateOf(FindPurchasedProductsState())
        private set

    var accessToken by mutableStateOf("")
        private set

    init{
        viewModelScope.launch {
            userTokenState = userTokenState.copy(
                isLoading = true
            )
            tokenUseCase.getAccessToken().collect{
                it?.let{accessToken ->
                    val accessTokenData: TokenItem = tokenUseCase.getTokenAccessData(accessToken)
                    Log.i(TAG, accessToken)
                    userTokenState = userTokenState.copy(
                        isLoading = false,
                        accessToken = accessToken,
                        accessTokenData =accessTokenData
                    )

                }
            }

        }
    }
}