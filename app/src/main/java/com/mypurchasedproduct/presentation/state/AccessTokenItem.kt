package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.domain.model.TokenModel

data class AccessTokenItem (
    val accessToken: String? = null,
    val accessTokenData: TokenModel? = null,
)