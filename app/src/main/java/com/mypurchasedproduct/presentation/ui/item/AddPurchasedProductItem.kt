package com.mypurchasedproduct.presentation.ui.item

import com.mypurchasedproduct.data.remote.model.response.ProductResponse

data class AddPurchasedProductItem(
    val product: ProductResponse? = null,
    val count: String = "",
    val unitMeasurement: Long = 0,
    val price: String = "",
)
