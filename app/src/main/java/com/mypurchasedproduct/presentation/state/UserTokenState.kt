package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.domain.TokenItem

data class UserTokenState (
    val isLoading: Boolean = false,
    val accessToken: String? = null,
    val accessTokenData: TokenItem? = null
)