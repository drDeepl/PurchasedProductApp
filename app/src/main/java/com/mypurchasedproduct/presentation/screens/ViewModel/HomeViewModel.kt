package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.domain.model.TokenModel
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.domain.usecases.TokenUseCase
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.state.FindPurchasedProductsState
import com.mypurchasedproduct.presentation.state.HomeState
import com.mypurchasedproduct.presentation.state.UserTokenState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val purchasedProductUseCase: PurchasedProductUseCase,
    private val tokenUseCase: TokenUseCase
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    var state by mutableStateOf(HomeState())
        private set
    var userTokenState by mutableStateOf(UserTokenState())
        private set

    var getPurchasedProductsState by mutableStateOf(FindPurchasedProductsState())
        private set


    init{
        viewModelScope.launch {
            Log.e(TAG, "RUN VIEW MODEL SCOPE")
            state = state.copy(
                isLoading = true
            )
            Log.wtf(TAG, "BEFORE REMOVE")
            tokenUseCase.removeAccessToken()
            Log.wtf(TAG, "AFTER REMOVE")
            tokenUseCase.getAccessToken().collect{accessToken ->
                Log.wtf(TAG, "RUN COLLECT GET ACCESS TOKEN")
                accessToken?.let {
                    Log.wtf(TAG, "ACCESS TOKEN $accessToken")
                    val tokenAccessData: TokenModel = tokenUseCase.getAccessTokenData(accessToken)
                    val differenceTime: Long = tokenAccessData.exp - System.currentTimeMillis()
                    if(differenceTime  <= 0){
                        Log.wtf(TAG, "DIFFERENCE TIME $differenceTime")
                        tokenUseCase.getRefreshToken().collect{refreshToken ->
//                            refreshToken?.let {
//                                Log.wtf(TAG, "REFRESH TOKEN $refreshToken")
//                            } : {Log.wtf(TAG, "REFRESH TOKEN NULL")}
                        }
                    }
                }
                TODO("IF ACCESS NULL -> REDIRECT SignUpScreen ELSE CHECK VALID IT")
                Log.wtf(TAG, "ACCESS IS NULL")
            }
        }
    }
    fun getPurchasedProducts(userId: Long, offset: Int){
        viewModelScope.launch {
            getPurchasedProductsState = getPurchasedProductsState.copy(
                isLoading =  true
            )

            purchasedProductUseCase.getAllPurchasedProductsCurrentUser(userId, offset).let{
                when(it){
                    is NetworkResult.Success -> {
                        it.data?.let{purchasedProducts ->
                            getPurchasedProductsState = getPurchasedProductsState.copy(
                                isLoading = false,
                                isSuccessResponse = true,
                                responseData = purchasedProducts
                            )

                        } ?: {getPurchasedProductsState = getPurchasedProductsState.copy(isLoading=false)}

                    }
                    is NetworkResult.Error ->{
                        getPurchasedProductsState = getPurchasedProductsState.copy(
                            isLoading = false,
                            isSuccessResponse = false,
                            error = it.message
                        )
                    }
                }
            }

        }
    }
}