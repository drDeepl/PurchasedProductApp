package com.mypurchasedproduct.presentation.utils

import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse

object InitModelsResponseUtils {

    val initPurchasedProductResponse: PurchasedProductResponse =  PurchasedProductResponse(
        id=0,
        userId=0,
        product= ProductResponse(id=0, name="", categoryId = 0),
        count =0,
        unitMeasurement = MeasurementUnitResponse(id=0, ""),
        price = 0.0,
        purchaseDate = ""
    )
}