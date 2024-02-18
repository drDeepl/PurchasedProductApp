package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.ui.item.AddPurchasedProductItem

data class AddPurchasedProductState(
    val isActive: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val product: ProductResponse? = null,
    val count: String = "",
    val measurementUnit: MeasurementUnitResponse? = null,
    val price: String = "",
    val isError: Boolean = false,
    val errors: List<String> = listOf(),

)
