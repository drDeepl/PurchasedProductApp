package com.mypurchasedproduct.presentation.state

data class SignUpState(
    val responseMessage: String? = null,
    val isSignUpSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isApplyTermsAndConditions: Boolean=false,
    val error: String? = null
)