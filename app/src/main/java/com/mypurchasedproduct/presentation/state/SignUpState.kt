package com.mypurchasedproduct.presentation.state

data class SignUpState(
    val username: String ="",
    val password: String = "",
    val isApplytermsAndCondition: Boolean = false,
    val responseMessage: String? = null,
    val isSignUpSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val isApplyTermsAndConditions: Boolean=false,
    val isError: Boolean = false,
    val error: String? = null
)