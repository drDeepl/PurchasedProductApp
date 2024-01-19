package com.mypurchasedproduct.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("isAdmin")
    val isAdmin: Boolean
)

