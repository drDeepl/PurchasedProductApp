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
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
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


    init {

//        viewModelScope.launch {
//            Log.e(TAG, "[START] VIEW MODEL SCOPE 1")
//            state  = state.copy(isLoading = true)
//            tokenUseCase.getAccessToken().take(1).collect{accessToken ->
//                accessToken?.let{
//                    Log.wtf(TAG, "ACCESS TOKEN IS EXISTS ${accessToken}")
//                }
//                Log.wtf(TAG, "ACCESS TOKEN IS NOT EXISTS ${accessToken}")
//            }
//
//            Log.e(TAG, "[FINISH] VIEW MODEL SCOPE 1")
//            state  = state.copy(isLoading = false)
//        }
    }

    fun checkAccessToken(){
        viewModelScope.launch {
            Log.e(TAG, "[START] VIEW MODEL SCOPE : CHECK ACCESS TOKEN")
            state  = state.copy(isLoading = true)

            tokenUseCase.getAccessToken().take(1).collect{accessToken ->
                accessToken?.let{
                    Log.wtf(TAG, "ACCESS TOKEN IS EXISTS ${accessToken}")
                    val accessTokenData: TokenModel = tokenUseCase.getAccessTokenData(accessToken)
                    userTokenState = userTokenState.copy(
                        accessToken = accessToken,
                        accessTokenData = accessTokenData
                    )
                    state = state.copy(
                        isSignIn = true,
                        isLoading = false
                    )
                } ?: Log.wtf(TAG, "ACCESS TOKEN IS NOT EXISTS ${accessToken}")
            }

            Log.e(TAG, "[FINISH] VIEW MODEL SCOPE : CHECK ACCESS TOKEN")
            state  = state.copy(isLoading = false)
        }
    }

    fun removeAccessToken(){
        viewModelScope.launch {
            tokenUseCase.removeAccessToken()
        }
    }
}