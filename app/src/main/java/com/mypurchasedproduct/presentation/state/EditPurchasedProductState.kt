package com.mypurchasedproduct.presentation.state

data class EditPurchasedProductState(
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
)
