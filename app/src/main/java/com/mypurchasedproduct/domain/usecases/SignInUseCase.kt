package com.mypurchasedproduct.domain.usecases

import android.util.Log
import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.data.repository.TokenRepository
import com.mypurchasedproduct.data.repository.UserRepository
import com.mypurchasedproduct.presentation.utils.NetworkResult
import java.security.PrivateKey
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) {

    private val TAG: String = this.javaClass.simpleName
    suspend fun invoke(signInRequest: SignInRequest): NetworkResult<TokenResponse> {
        val result: NetworkResult<TokenResponse> = userRepository.signIn(signInRequest = signInRequest)
        if(result is NetworkResult.Success){
            result.data?.let{tokenResponse ->
                Log.i(TAG, "SET REFRESH TOKEN")
                tokenRepository.setRefreshToken(tokenResponse.refreshToken)
                tokenRepository.setAccessToken(tokenResponse.accessToken)
            }
        }
        return result
    }


}