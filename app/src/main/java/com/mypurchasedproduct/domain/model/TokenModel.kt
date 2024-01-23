package com.mypurchasedproduct.domain.model

data class TokenModel(
    val id: Long,
    val sub: String,
    val isAdmin: Boolean,
    val exp: Int,
)