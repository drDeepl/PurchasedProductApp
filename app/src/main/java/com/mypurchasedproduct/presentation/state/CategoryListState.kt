package com.mypurchasedproduct.presentation.state

data class CategoryListState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
)
