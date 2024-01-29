package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem

data class AddPurchasedProductState(
    val isActive: Boolean = false,
    val isSuccessAdd: Boolean? = null,
    val isLoading: Boolean = false,

)
