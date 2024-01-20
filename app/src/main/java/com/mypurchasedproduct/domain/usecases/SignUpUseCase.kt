package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.data.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(signUpRequest: SignUpRequest) = userRepository.signUp(signUpRequest = signUpRequest)


}