package com.mypurchasedproduct.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    private val type : String,
    private val accessToken: String,
    private val refreshToken: String
)
