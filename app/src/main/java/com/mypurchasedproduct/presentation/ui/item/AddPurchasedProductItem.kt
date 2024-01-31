package com.mypurchasedproduct.presentation.ui.item

import com.mypurchasedproduct.data.remote.model.response.ProductResponse

data class AddPurchasedProductItem(
    val product: ProductResponse? = null,
    val count: String = "1",
    val unitMeasurement: Long = 1,
    val price: String = "0.0",
)
