package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem

data class AddPurchasedProductState(
    val isActive: Boolean = false,
    val isSuccess: Boolean? = null,
    val isLoading: Boolean = false,
    val product: PurchasedProductResponse? = null,
    val isError: Boolean = false,
    val error: String? = null,

)
