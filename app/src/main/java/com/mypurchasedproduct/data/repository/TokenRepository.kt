package com.mypurchasedproduct.data.repository

import com.mypurchasedproduct.data.local.DataStoreManager
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.request.RefreshTokenRequest
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class TokenRepository  @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val remoteDataSource: RemoteDataSource
): BaseApiResponse(){

    suspend fun setAccessToken(accessToken:String){
        dataStoreManager.saveAccessToken(accessToken)
    }

    suspend fun setRefreshToken(refreshToken: String){
        dataStoreManager.saveRefreshToken(refreshToken)
    }

    suspend fun getAccessToken() = dataStoreManager.getAccessToken()
    suspend fun getRefreshToken() = dataStoreManager.getRefreshToken()

    suspend fun updateAccessToken(refreshToken: String): NetworkResult<TokenResponse>{
        val refreshTokenRequest = RefreshTokenRequest(refreshToken)
        return  safeApiCall { remoteDataSource.updateAccessToken(refreshTokenRequest)}
    }

    suspend fun removeAccessToken() = dataStoreManager.removeAccessToken()
    suspend fun removeRefreshToken()= dataStoreManager.removeRefreshToken()








}