package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.ProductResponse

data class AddProductState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val msg: String? = null,
    val product: ProductResponse? = null
)
