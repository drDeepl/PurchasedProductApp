package com.mypurchasedproduct.data.remote.model.request

data class EditPurchasedProductRequest(
    val productId: Long,
    val count: Int,
    val price: Double,
    val userId: Long,
    val unitMeasurementId: Long,
    val purchaseDate: Long,
)
