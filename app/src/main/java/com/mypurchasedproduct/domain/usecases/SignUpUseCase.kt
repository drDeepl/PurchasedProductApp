package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.data.remote.model.response.MessageResponse
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import com.mypurchasedproduct.data.repository.UserRepository
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(username: String, password:String): NetworkResult<MessageResponse> {
        if(username.isEmpty() || password.isEmpty()){
            return NetworkResult.Error("необходимо заполнить все поля")
        }
        else{
            return userRepository.signUp(signUpRequest = SignUpRequest(username, password))

        }
    }


}