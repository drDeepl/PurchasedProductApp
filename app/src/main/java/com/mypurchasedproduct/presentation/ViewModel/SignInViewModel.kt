package com.mypurchasedproduct.presentation.ViewModel

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel(){

    private val TAG = this.javaClass.simpleName

//    var state by mutableStateOf(SignInState())
//    private set

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()


    init{

    }

    fun onUsernameChange(username: String){
        viewModelScope.launch {
            _signInState.update { signInState ->
                signInState.copy(
                    username = username
                )
            }
        }
    }

    fun onPasswordChange(password: String){
        viewModelScope.launch {
            _signInState.update { signInState ->
                signInState.copy(
                    password = password
                )
            }
        }
    }

    fun toSignIn(){
        Log.wtf(TAG, "TO SIGN IN")
        viewModelScope.launch {
            _signInState.update {signInState ->
                signInState.copy(
                    isLoading = true
                )
            }
            signInUseCase.invoke(SignInRequest(signInState.value.username, signInState.value.password)).let{
                when(it){
                    is NetworkResult.Success ->{
                        it.data?. let {
                            _signInState.update { signInState ->
                                signInState.copy(
                                    responseData = it,
                                    isLoading = false,
                                    isSuccess = true
                                )
                            }

                        } ?: {

                            _signInState.update { signInState ->
                                signInState.copy(
                                    isLoading = false,
                                    isError = true,
                                    error = "токен не найден"
                                )
                            }
                        }
                    }
                    is NetworkResult.Error ->{
                        _signInState.update { signInState ->
                            signInState.copy(
                                isLoading = false,
                                isError = true,
                                error = it.message.toString()
                            )
                        }
                    }
                }
            }
        }
    }

//    fun toSignIn(username: String, password:String){
//        Log.wtf(TAG, "TO SIGN IN")
//        viewModelScope.launch {
//            _signInState.update {signInState ->
//                signInState.copy(
//                    isLoading = true
//                )
//            }
//
//            signInUseCase.invoke(SignInRequest(username, password)).let{
//                when(it){
//                    is NetworkResult.Success ->{
//                        it.data?. let {
//                            _signInState.update { signInState ->
//                                signInState.copy(
//                                    responseData = it,
//                                    isLoading = false,
//                                    isSuccess = true
//                                )
//                             }
//
//                        } ?: {
//
//                            _signInState.update { signInState ->
//                                signInState.copy(
//                                    isLoading = false,
//                                    isError = true,
//                                    error = "токен не найден"
//                                )
//                            }
//                        }
//                    }
//                    is NetworkResult.Error ->{
//                        _signInState.update { signInState ->
//                            signInState.copy(
//                                isLoading = false,
//                                isError = true,
//                                error = it.message.toString()
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
    fun defaultState(){
        Log.wtf(TAG, "DEAFULT STATE")
        viewModelScope.launch {
            _signInState.value = SignInState()
        }

    }
}

