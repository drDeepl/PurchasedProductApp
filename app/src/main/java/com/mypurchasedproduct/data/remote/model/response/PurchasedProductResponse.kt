package com.mypurchasedproduct.data.remote.model.response

data class PurchasedProductResponse(
    val id: Long,
    val userId: Long,
    val product: ProductResponse,
    val count: Int,
    val unitMeasurement: MeasurementUnitResponse,
    val price: Double,
    val purchaseDate: String
)
