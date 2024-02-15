package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.domain.model.TokenModel
import com.mypurchasedproduct.domain.usecases.SignInUseCase
import com.mypurchasedproduct.domain.usecases.SignUpUseCase
import com.mypurchasedproduct.domain.usecases.TokenUseCase
import com.mypurchasedproduct.presentation.state.AccessTokenItem
import com.mypurchasedproduct.presentation.state.AuthState
import com.mypurchasedproduct.presentation.state.CheckTokenState
import com.mypurchasedproduct.presentation.state.SignInState
import com.mypurchasedproduct.presentation.state.SignUpState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AuthViewModel  @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val tokenUseCase: TokenUseCase,
): ViewModel(){

    private val TAG = this.javaClass.simpleName

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    private var _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    private val _tokenState =  MutableStateFlow(CheckTokenState())
    val tokenState = _tokenState.asStateFlow()

    private val _accessTokenItem = MutableStateFlow(AccessTokenItem())
    val accessTokenItem = _accessTokenItem.asStateFlow()

    val actionTabs: List<String> = listOf("вход", "регистрация")
    private val _currentTab = MutableStateFlow(actionTabs[0])
    val currentTab = _currentTab.asStateFlow()

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    var animated = mutableStateOf(false)

    init{
        Log.wtf(TAG, "INIT VIEW MODEL")
        viewModelScope.launch {
            checkAccessToken()
        }
    }



    fun updateAccessToken(){
        viewModelScope.launch {
            Log.wtf(TAG, "UPDATE ACCESS TOKEN")
            val refreshToken: String? = tokenUseCase.getRefreshToken().first()
            if(refreshToken != null){
                val networkResult = this.async { tokenUseCase.updateAccessToken(refreshToken)}.await()
                when(networkResult){
                    is NetworkResult.Success ->{
                        val newAccessToken: String = networkResult.data?.accessToken.toString()
                        val newAccessTokenData: TokenModel = tokenUseCase.getAccessTokenData(newAccessToken)
                        _tokenState.update {tokenState ->
                        tokenState.copy(
                            isActive = false,
                            isComplete = true,
                        )
                        }
                        _accessTokenItem.update { accessTokenItem ->
                            accessTokenItem.copy(
                                accessToken = newAccessToken,
                                accessTokenData = newAccessTokenData
                            )
                        }
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                isSignIn = true
                            )
                        }
                    }
                    is NetworkResult.Error ->{
                        Log.wtf(TAG, "NETWORK ERROR TOKEN IS NOT EXISTS")
                        _tokenState.update { tokenState ->
                            tokenState.copy(
                                isActive = false,
                                isError = true,
                                error =  networkResult.message
                            )
                        }
                        _state.update { state ->
                            state.copy(isLoading = false)
                        }
                    }
                }
            }
            else{
                _tokenState.update {tokenState ->
                tokenState.copy(isError = true, error = "Токен не найден")
                }
            }

        }
    }

    fun checkAccessToken(){
        Log.wtf(TAG, "CHECK ACCESS TOKEN")
        viewModelScope.launch {
            Log.e(TAG, "[START] VIEW MODEL SCOPE : CHECK ACCESS TOKEN")
            _tokenState.update { tokenState ->
                tokenState.copy(isActive = true)
            }
            tokenUseCase.getAccessToken().take(1).collect{accessToken ->
                if(accessToken != null){
                    Log.wtf(TAG, "ACCESS TOKEN IS EXISTS ${accessToken}")
                    val accessTokenData: TokenModel = tokenUseCase.getAccessTokenData(accessToken)
                    val difference: Long =System.currentTimeMillis() - accessTokenData.exp
                    Log.wtf(TAG, "DIFFERENCE TIME TOKEN: ${difference}")
                    Log.w(TAG, "Current timestamp ${System.currentTimeMillis()}")
                    Log.w(TAG, "Timestamp from token ${Instant.ofEpochSecond(accessTokenData.exp.toLong()).epochSecond}")
                    if( difference > 0){
                        updateAccessToken()
                    }
                    else{
                        _state.update { state ->
                            state.copy(isLoading = false, isSignIn = true)
                        }
                        _tokenState.update { tokenState ->
                            tokenState.copy(
                                isActive = false,
                                isComplete = true,
                            )
                        }
                        _accessTokenItem.update { accessTokenItem ->
                            accessTokenItem.copy(
                                accessToken = accessToken,
                                accessTokenData = accessTokenData
                            )
                        }
                    }
                }
                else{
                    Log.wtf(TAG, "ACCESS TOKEN IS NOT EXISTS")
                    _tokenState.update{tokenState ->
                        tokenState.copy(
                            isActive = false,
                            isComplete = true,
                        )
                    }
                }
                _state.update { state ->
                    state.copy(isLoading = false)
                }
            }
            Log.e(TAG, "[FINISH] VIEW MODEL SCOPE : CHECK ACCESS TOKEN")
        }
    }
    fun signOut(){
        viewModelScope.launch {
            val removedAccessToken = this.async { tokenUseCase.removeAccessToken() }
            removedAccessToken.await()
            _state.update { state ->
                state.copy(
                    isSignIn = false
                )
            }
        }
    }

    fun setSignIn(isSignIn: Boolean){
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    isSignIn = isSignIn
                )

            }
        }

    }

    fun setLoading(isLoading: Boolean){
        viewModelScope.launch{
            Log.wtf(TAG, "SET LOADING")
            _state.update { state ->
                state.copy(
                    isLoading = isLoading
                )
            }
        }
    }

    fun setCurrentAction(id: Int){

        viewModelScope.launch {
            animated.value = !animated.value
            _currentTab.update {
                actionTabs[id]
            }

        }
    }

    fun defaultSignInState(){
        Log.wtf(TAG, "DEAFULT STATE")
        viewModelScope.launch {
            _signInState.value = SignInState()
        }
    }
}