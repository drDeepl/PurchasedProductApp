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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel(){

    private val TAG = this.javaClass.simpleName
    var state by mutableStateOf(SignUpState())
        private set


    init{

    }

    fun toSignUp(signUpRequest: SignUpRequest){

        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            signUpUseCase.invoke(signUpRequest).let{
                when(it){
                    is NetworkResult.Success ->{
                        state = state.copy(
                            isLoading = false,
                            responseMessage = it.data?.message,
                            isSignUpSuccess = true
                        )
                    }
                    is NetworkResult.Error ->{
                        state = state.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }
                }
            }
        }
    }

    fun setIsApplyTermsAndConditions(value: Boolean) {
        state = state.copy(
            isApplyTermsAndConditions = value
        )
    }

    fun setError(msg: String?){
        state = state.copy(
            error = msg
        )
    }

    fun defaultState(){
        state = SignUpState()
    }

}