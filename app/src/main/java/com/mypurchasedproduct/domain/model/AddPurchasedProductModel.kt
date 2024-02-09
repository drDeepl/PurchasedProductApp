package com.mypurchasedproduct.domain.model

import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import java.sql.Timestamp

data class AddPurchasedProductModel(
    val product: ProductResponse?,
    val count: String,
    val unitMeasurement: Long,
    val price: String
)
