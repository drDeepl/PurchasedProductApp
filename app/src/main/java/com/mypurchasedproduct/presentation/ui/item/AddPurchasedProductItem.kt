package com.mypurchasedproduct.presentation.ui.item

data class AddPurchasedProductItem(
    val categoryId: Long = 1,
    val productId: Long = 1,
    val count: String? = "1",
    val unitMeasurement: Long = 1,
    val price: String? = "0.0",
)
