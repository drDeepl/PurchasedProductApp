package com.mypurchasedproduct.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val message: String)
