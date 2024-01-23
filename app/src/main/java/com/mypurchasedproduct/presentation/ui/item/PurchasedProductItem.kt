package com.mypurchasedproduct.presentation.ui.item

data class PurchasedProductItem(
    val id: Long,
    val categoryId: Long,
    val productName: String,
    val count: Int,
    val unitMeasurement: String,
    val price: Double,
)
