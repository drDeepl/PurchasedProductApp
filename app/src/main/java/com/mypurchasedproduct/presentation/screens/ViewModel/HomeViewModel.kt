package com.mypurchasedproduct.presentation.screens.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.domain.model.TokenModel
import com.mypurchasedproduct.domain.usecases.MeasurementUnitUseCase
import com.mypurchasedproduct.domain.usecases.ProductUseCase
import com.mypurchasedproduct.domain.usecases.PurchasedProductUseCase
import com.mypurchasedproduct.domain.usecases.TokenUseCase
import com.mypurchasedproduct.presentation.state.FindPurchasedProductsState
import com.mypurchasedproduct.presentation.state.HomeState
import com.mypurchasedproduct.presentation.state.AccessTokenItem
import com.mypurchasedproduct.presentation.state.AddPurchasedProductState
import com.mypurchasedproduct.presentation.state.CheckTokenState
import com.mypurchasedproduct.presentation.state.FindMeasurementUnitsState
import com.mypurchasedproduct.presentation.state.FindProductsState
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val purchasedProductUseCase: PurchasedProductUseCase,
    private val tokenUseCase: TokenUseCase,
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    var state by mutableStateOf(HomeState())
        private set

    var checkTokenState by mutableStateOf(CheckTokenState())
        private set




    var accessTokenItem by mutableStateOf(AccessTokenItem())
        private set

    var getPurchasedProductsState by mutableStateOf(FindPurchasedProductsState())
        private set






    init {
        Log.e(TAG, "INIT VIEW MODEL")
    }

    fun checkAccessToken(){
        Log.wtf(TAG, "CHECK ACCESS TOKEN")
        viewModelScope.launch {
            Log.e(TAG, "[START] VIEW MODEL SCOPE : CHECK ACCESS TOKEN")
            state = state.copy(isLoading=true)
            checkTokenState = checkTokenState.copy(
                isActive = true
            )
            tokenUseCase.getAccessToken().take(1).collect{accessToken ->
                if(accessToken != null){
                    Log.wtf(TAG, "ACCESS TOKEN IS EXISTS ${accessToken}")
                    val accessTokenData: TokenModel = tokenUseCase.getAccessTokenData(accessToken)
                    val difference: Long =System.currentTimeMillis() - accessTokenData.exp
                    Log.wtf(TAG, "DIFFERENCE TIME TOKEN: ${difference}")
                    Log.w(TAG, "Current timestamp ${System.currentTimeMillis()}")
                    Log.w(TAG, "Timestamp from token ${accessTokenData.exp}")
                    if( difference> 0){
                        val refreshToken: String? = tokenUseCase.getRefreshToken().first()
                        if(refreshToken != null){
                            val networkResult = this.async { tokenUseCase.updateAccessToken(refreshToken)}.await()
                            when(networkResult){
                                is NetworkResult.Success ->{
                                    val newAccessToken: String? = networkResult.data?.accessToken
                                    val newAccessTokenData: TokenModel = tokenUseCase.getAccessTokenData(accessToken)
                                    checkTokenState = checkTokenState.copy(
                                        isActive = false,
                                        isComplete = true,
                                    )
                                    accessTokenItem = accessTokenItem.copy(
                                        accessToken = newAccessToken,
                                        accessTokenData = newAccessTokenData
                                    )
                                    state = state.copy(
                                        isSignIn = true,
                                        isLoading=false
                                    )
                                }
                                is NetworkResult.Error ->{
                                    Log.wtf(TAG, "NETWORK ERROR TOKEN IS NOT EXISTS")
                                    checkTokenState = checkTokenState.copy(
                                        isActive = false,
                                        isError = true,
                                        error =  networkResult.message
                                    )
                                    state = state.copy(
                                        isSignIn = false,
                                        isLoading = false
                                    )

                                }
                            }
                        }

                    }
                    else{
                        checkTokenState = checkTokenState.copy(
                            isActive = false,
                            isComplete = true,
                        )
                        accessTokenItem = accessTokenItem.copy(
                            accessToken = accessToken,
                            accessTokenData = accessTokenData
                        )
                        state = state.copy(
                            isSignIn = true,
                            isLoading = false
                        )
                    }
                }
                else{
                    Log.wtf(TAG, "ACCESS TOKEN IS NOT EXISTS ${accessToken}")
                    checkTokenState = checkTokenState.copy(
                        isActive = false,
                        isComplete = true,
                    )
                    state = state.copy(
                        isSignIn = false,
                        isLoading = false
                    )

                }

            }
            Log.e(TAG, "[FINISH] VIEW MODEL SCOPE : CHECK ACCESS TOKEN")
        }
    }

    fun signOut(){
        state = state.copy(
            isLoading = true
        )
        viewModelScope.launch {
            val removedAccessToken = this.async { tokenUseCase.removeAccessToken() }
            removedAccessToken.await()
            state = state.copy(
                isLoading = false,
                isSignIn = null,
                error = null
            )
            getPurchasedProductsState = FindPurchasedProductsState()
            accessTokenItem = AccessTokenItem()
            checkTokenState = CheckTokenState()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getPurchasedProductCurrentUser(offset: Int){
        getPurchasedProductsState = getPurchasedProductsState.copy(
            isLoading = true
        )
        viewModelScope.launch{
            Log.wtf(TAG, "GET PURCHASED PRODUCT CURRENT USER")
            accessTokenItem.accessTokenData?.let{tokenModel->
                val purchasedProducts = this.async { purchasedProductUseCase.getAllPurchasedProductsCurrentUser(tokenModel.id, offset) }.await()
                when(purchasedProducts){
                    is NetworkResult.Success -> {
                        purchasedProducts.data?.let{
                            getPurchasedProductsState = getPurchasedProductsState.copy(
                                isActive = false,
                                purchasedProducts = it,
                                isSuccessResponse = true,
                                isLoading = false,
                            )
                        }

                    }
                    is NetworkResult.Error ->{
                        getPurchasedProductsState = getPurchasedProductsState.copy(
                            isActive = false,
                            error = purchasedProducts.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }


    fun setGetPurchasedProduct(){
        Log.wtf(TAG, "SET GET PURCHASED PRODUCTS")
        getPurchasedProductsState = getPurchasedProductsState.copy(
            isActive = true
        )
    }



    fun defaultHomeState(){
        state = HomeState()
    }
}