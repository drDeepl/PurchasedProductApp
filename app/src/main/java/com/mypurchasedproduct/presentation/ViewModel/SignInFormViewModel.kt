package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mypurchasedproduct.presentation.state.SignInFormState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInFormViewModel: ViewModel() {

    private val TAG = this.javaClass.simpleName

    private val _signInFormState = MutableStateFlow(SignInFormState())
    val signInFormState = _signInFormState.asStateFlow()

    fun onChangeUsername(username: String){
        Log.wtf(TAG, "onChangeUsername")
        _signInFormState.update {
            it.copy(
                username = username
            )
        }
    }

    fun onChangePassword(password: String){
        Log.wtf(TAG, "onChangeUsername")
        _signInFormState.update {
            it.copy(
                password = password
            )
        }
    }

}