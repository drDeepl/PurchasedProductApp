package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.remote.model.request.SignInRequest
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.data.repository.TokenRepository
import com.mypurchasedproduct.data.repository.UserRepository
import java.security.PrivateKey
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun invoke(signInRequest: SignInRequest) = userRepository.signIn(signInRequest = signInRequest)
    suspend fun setAccessTokenStore(accessToken: String) = tokenRepository.setAccessToken(accessToken)

}