package com.mypurchasedproduct.data.repository

import android.util.Log
import com.mypurchasedproduct.data.local.DataStoreManager
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

// Сюда же можно добаивть локальный DataSource
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreManager: DataStoreManager
) : BaseApiResponse(){
    private val TAG = this.javaClass.simpleName
    suspend fun signUp(signUpRequest: SignUpRequest): NetworkResult<MessageResponse>{
        Log.i(TAG, "SIGN UP")
        return safeApiCall{remoteDataSource.signUp(signUpRequest)}
    }

    suspend fun signIn(signInRequest: SignInRequest): NetworkResult<TokenResponse>{
        return safeApiCall{remoteDataSource.signIn(signInRequest)}
    }


}