package com.mypurchasedproduct.presentation.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.domain.usecases.SignUpUseCase
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    private val _signUpResponse = MutableLiveData<NetworkResult<MessageResponse>>()
    val signUpResponse: LiveData<NetworkResult<MessageResponse>>
        get() = _signUpResponse

    val _isLoading = MutableLiveData(false)
    val isLoading: MutableLiveData<Boolean> get() = _isLoading



    init{

    }

    fun toSignUp(signUpRequest: SignUpRequest){
        Log.i(TAG, "TO SIGN UP: ${signUpRequest.username} ${signUpRequest.password}")
        _isLoading.value = true
        viewModelScope.launch {
            signUpUseCase.invoke(signUpRequest).let{
                _signUpResponse.value = it
            }
        }
        Log.i(TAG, _signUpResponse.value?.statusCode.toString())

    }
}