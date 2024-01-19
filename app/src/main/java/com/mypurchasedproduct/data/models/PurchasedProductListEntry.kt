package com.mypurchasedproduct.data.models

data class PurchasedProductListEntry(
    val id: Long,
    val userId: Long,
    val productd: Long,
    val count: Int,
    val unitMeasurement: String,
    val price: Double,
    val purchasedDate: String
)
