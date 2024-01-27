package com.mypurchasedproduct.domain.model

import java.sql.Timestamp

data class AddPurchasedProductModel(
    val productId: Long? = null,
    val count: Int? = null,
    val unitMeasurement: Long? = null,
    val price: Double? = null,
    val purchasedDatetime: Timestamp? = null
)
