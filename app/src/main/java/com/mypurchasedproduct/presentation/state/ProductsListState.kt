package com.mypurchasedproduct.presentation.state

data class ProductsListState(
    val isActive: Boolean,
    val isLoading:Boolean,
    val isError: Boolean,
    val error: String,
)
