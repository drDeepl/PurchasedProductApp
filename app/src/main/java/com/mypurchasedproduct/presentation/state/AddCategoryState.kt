package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.CategoryResponse

data class AddCategoryState(
    val isActive: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val addedCategory: CategoryResponse? = null
)
