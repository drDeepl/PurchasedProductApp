package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.ProductResponse

data class ProductBottomSheetState(
    val isActive: Boolean = false,
    val selectedProduct: ProductResponse? = null,
)
