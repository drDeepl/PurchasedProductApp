package com.mypurchasedproduct.presentation.state

import java.lang.Error

data class AddProductFormState(
    val isActive: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
)
