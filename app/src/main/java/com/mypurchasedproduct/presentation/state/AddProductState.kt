package com.mypurchasedproduct.presentation.state

data class AddProductState(
    val isActive: Boolean =false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null
)
