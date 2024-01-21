package com.mypurchasedproduct.data.remote.model.response

data class TokenResponse(
     val type : String,
     val accessToken: String,
     val refreshToken: String
)
