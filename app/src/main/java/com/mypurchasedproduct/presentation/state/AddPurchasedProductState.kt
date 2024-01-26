package com.mypurchasedproduct.presentation.state

data class AddPurchasedProductState(
    val isActive: Boolean = false,
    val isSuccessAdd: Boolean? = null,
    val isLoading: Boolean = false
)
