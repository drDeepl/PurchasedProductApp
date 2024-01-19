package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.remote.PurchasedProductApi
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository
) {

    suspend fun invoke(signUpRequest: SignUpRequest) = purchasedProductRepository.signUp(signUpRequest = signUpRequest)


}