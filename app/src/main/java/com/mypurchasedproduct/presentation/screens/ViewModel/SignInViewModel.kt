package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.domain.usecases.SignInUseCase
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.state.SignInState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    var state by mutableStateOf(SignInState())
    private set


    init{

    }

    fun toSignIn(username: String, password:String){
        Log.wtf(TAG, "TO SIGN IN")
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            signInUseCase.invoke(SignInRequest(username, password)).let{
                when(it){
                    is NetworkResult.Success ->{
                        it.data?. let {
                            state = state.copy(
                                responseData = it,
                                isLoading = false,
                                isSignInSuccess = true
                            )
                        } ?: {
                            state = state.copy(
                                isLoading = false,
                                isSignInSuccess = false,
                                error = "токен не найден"
                            )
                        }
                    }
                    is NetworkResult.Error ->{
                        state = state.copy(
                            error = it.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun defaultState(){
        state = SignInState()
    }
}