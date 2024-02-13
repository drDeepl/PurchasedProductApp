package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.TokenResponse

data class SignInState(
    val responseData: TokenResponse? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
)

