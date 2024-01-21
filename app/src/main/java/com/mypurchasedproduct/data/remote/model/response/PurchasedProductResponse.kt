package com.mypurchasedproduct.data.remote.model.response

data class PurchasedProductResponse(
    val id: Long,
    val userId: Long,
    val categoryId: Long,
    val productName: String,
    val count: Int,
    val unitMeasurement: String,
    val price: Double,
    val purchasedDate: String
)
