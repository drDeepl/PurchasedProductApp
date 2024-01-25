package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.domain.model.TokenModel

data class CheckTokenState(
    val isActive: Boolean = false,
    val isComplete: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val data: TokenModel? = null,

)