package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.ProductResponse

data class FindProductsState(
    val isLoading: Boolean = true,
    val isUpdating: Boolean = true,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val products: List<ProductResponse> = listOf()
)
