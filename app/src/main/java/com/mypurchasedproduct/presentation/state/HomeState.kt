package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.TokenResponse

data class HomeState (
    val isLoading: Boolean,
    val isError: Boolean,
    val error: String
)