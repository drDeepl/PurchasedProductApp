package com.mypurchasedproduct.domain

data class TokenItem(
    val id: Long,
    val sub: String,
    val isAdmin: Boolean,
    val exp: Int,
)