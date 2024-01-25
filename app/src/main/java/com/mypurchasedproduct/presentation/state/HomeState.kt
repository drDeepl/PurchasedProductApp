package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.TokenResponse

data class HomeState (
    val isSignIn: Boolean? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)