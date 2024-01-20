package com.mypurchasedproduct.data.local

import androidx.datastore.core.Serializer
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import java.io.InputStream
import java.io.OutputStream


object TokenSerializer: Serializer<TokenResponse> {
    override val defaultValue: TokenResponse
        get() = TokenResponse()

    override suspend fun readFrom(input: InputStream): TokenResponse{}
    override suspend fun writeTo(t: TokenResponse, output: OutputStream){

    }
}