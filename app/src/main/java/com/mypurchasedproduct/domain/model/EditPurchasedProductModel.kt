package com.mypurchasedproduct.domain.model

import com.mypurchasedproduct.data.remote.model.response.ProductResponse

data class EditPurchasedProductModel(
    val id: Long,
    val product: ProductResponse?,
    val count: String,
    val userId: Long,
    val unitMeasurement: Long,
    val price: String,
    val purchaseDate: String
)
