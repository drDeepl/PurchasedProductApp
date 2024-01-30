package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.CategoryResponse

data class GetCategoriesState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val categories: List<CategoryResponse>? = null,
)
