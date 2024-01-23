package com.mypurchasedproduct.domain.model

data class PurchasedProductModel(
    val id: Long,
    val userId: Long,
    val categoryId: Long,
    val productName: String,
    val count: Int,
    val unitMeasurement: String,
    val price: Double,
    val purchasedDate: String
)