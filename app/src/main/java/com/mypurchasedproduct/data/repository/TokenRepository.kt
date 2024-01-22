package com.mypurchasedproduct.data.repository

import com.mypurchasedproduct.data.local.DataStoreManager
import javax.inject.Inject

class TokenRepository  @Inject constructor(
    private val dataStoreManager: DataStoreManager
){

    suspend fun setAccessToken(accessToken:String){
        dataStoreManager.saveAccessToken(accessToken)
    }

    suspend fun getAccessToken() = dataStoreManager.getAccessToken()

}