package com.mypurchasedproduct.presentation.screens.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.domain.usecases.SignUpUseCase
import com.mypurchasedproduct.presentation.state.SignUpState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel(){

    private val TAG = this.javaClass.simpleName
//    var state by mutableStateOf(SignUpState())
//        private set

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()


    init{

    }

    fun onUsernameChange(username: String){
        viewModelScope.launch {
            _signUpState.update { signUpState ->
                signUpState.copy(
                    username = username
                )
            }
        }
    }

    fun onPasswordChange(password: String){
        viewModelScope.launch {
            _signUpState.update { signUpState ->
                signUpState.copy(
                    password = password
                )
            }
        }
    }


    fun toSignUp(){
        viewModelScope.launch {
            _signUpState.update { signUpState ->
                signUpState.copy(isLoading = true)
            }

            signUpUseCase.invoke(signUpState.value.username, signUpState.value.password).let{
                when(it){
                    is NetworkResult.Success ->{
                        _signUpState.update { signUpState ->
                            signUpState.copy(
                                isLoading = false,
                                responseMessage = it.data?.message,
                                isSignUpSuccess = true)
                        }
                    }
                    is NetworkResult.Error ->{
                        _signUpState.update { signUpState ->
                            signUpState.copy(
                                isLoading = false,
                                error = it.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun setIsApplyTermsAndConditions(value: Boolean) {
        viewModelScope.launch {
            _signUpState.update { signUpState ->
                signUpState.copy(isApplyTermsAndConditions = value)
            }
        }
    }

    fun setError(msg: String?){
        viewModelScope.launch {
            _signUpState.update { signUpState ->
                signUpState.copy(error = msg.toString())
            }
        }
    }
}