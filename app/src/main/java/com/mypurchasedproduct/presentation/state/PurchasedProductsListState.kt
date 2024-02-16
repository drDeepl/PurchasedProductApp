package com.mypurchasedproduct.presentation.state

data class PurchasedProductsListState (
    val isLoading: Boolean,
    val isUpdate: Boolean,
    val isError: Boolean,
    val error: String
)

