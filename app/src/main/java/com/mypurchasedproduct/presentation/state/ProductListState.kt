package com.mypurchasedproduct.presentation.state

data class ProductListState(
    val isLoading: Boolean=false,
    val isError: Boolean=false,
    val isSuccess: Boolean=false
)
