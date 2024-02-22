package com.mypurchasedproduct.presentation.ui.item

import com.mypurchasedproduct.data.remote.model.response.CategoryResponse

data class ProductItem(
    val category: CategoryResponse = CategoryResponse(0, ""),
    val productName: String = "",
)
