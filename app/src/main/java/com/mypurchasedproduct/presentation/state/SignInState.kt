package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.TokenResponse

data class SignInState(
    val responseData: TokenResponse? = null,
    val isSignInSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

