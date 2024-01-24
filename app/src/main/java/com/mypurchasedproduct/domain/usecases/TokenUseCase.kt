package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.data.repository.TokenRepository
import com.mypurchasedproduct.domain.model.TokenModel
import com.mypurchasedproduct.presentation.utils.JwtTokenUtils
import com.mypurchasedproduct.presentation.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import javax.inject.Inject

class TokenUseCase  @Inject constructor(
    private val tokenRepository: TokenRepository
){
    private val jwtTokenUtils: JwtTokenUtils = JwtTokenUtils()


    private val TAG: String = this.javaClass.simpleName
    suspend fun getAccessToken() = tokenRepository.getAccessToken()
    fun getAccessTokenData(accessToken: String) = jwtTokenUtils.getTokenAccessData(accessToken)
    suspend fun getRefreshToken() = tokenRepository.getRefreshToken()

    suspend fun updateAccessToken(refreshToken: String) : NetworkResult<TokenResponse>{
        val networkResult: NetworkResult<TokenResponse> = tokenRepository.updateAccessToken(refreshToken)
        if(networkResult is NetworkResult.Success){
            networkResult.data?.let{
                tokenRepository.setAccessToken(it.accessToken)
                tokenRepository.setRefreshToken(it.refreshToken)

            }

        }
        return networkResult
    }

     suspend fun removeAccessToken() = tokenRepository.removeAccessToken()

    suspend fun removeRefreshToken() = tokenRepository.removeRefreshToken()


}