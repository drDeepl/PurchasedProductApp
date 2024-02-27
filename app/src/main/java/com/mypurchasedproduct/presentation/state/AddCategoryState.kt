package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.CategoryResponse

data class AddCategoryState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
