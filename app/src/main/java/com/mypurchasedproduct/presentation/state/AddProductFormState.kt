package com.mypurchasedproduct.presentation.state

import java.lang.Error

data class AddProductFormState(
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null
)
