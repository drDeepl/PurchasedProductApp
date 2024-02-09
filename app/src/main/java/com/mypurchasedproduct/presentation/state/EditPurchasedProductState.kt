package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse

data class EditPurchasedProductState(
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val purchasedProduct: PurchasedProductResponse? = null
)
