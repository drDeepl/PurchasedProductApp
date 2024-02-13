package com.mypurchasedproduct.presentation.state

data class SignInFormState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
    val username: String = "",
    val password: String = ""
)
