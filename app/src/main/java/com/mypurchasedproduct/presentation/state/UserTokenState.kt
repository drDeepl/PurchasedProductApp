package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.domain.model.TokenModel

data class UserTokenState (
    val isLoading: Boolean = false,
    val accessToken: String? = null,
    val accessTokenData: TokenModel? = null
)