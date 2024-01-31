package com.mypurchasedproduct.data.remote.model.request

import java.sql.Timestamp
import java.time.LocalDateTime

data class AddPurchasedProduct(
    private val userId: Long,
    private val productId: Long,
    private val count: Int,
    private val unitMeasurement: Long,
    private val price:Double,
    private val purchasedDateTime: Timestamp,
)
