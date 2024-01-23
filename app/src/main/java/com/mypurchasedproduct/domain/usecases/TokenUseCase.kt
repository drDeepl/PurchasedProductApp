package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.repository.TokenRepository
import com.mypurchasedproduct.domain.model.TokenModel
import com.mypurchasedproduct.presentation.utils.JwtTokenUtils
import org.json.JSONObject
import javax.inject.Inject

class TokenUseCase  @Inject constructor(
    private val tokenRepository: TokenRepository
){
    private val jwtTokenUtils: JwtTokenUtils = JwtTokenUtils()


    private val TAG: String = this.javaClass.simpleName
    suspend fun getAccessToken() = tokenRepository.getAccessToken()

    fun getTokenAccessData(accessToken: String): TokenModel {
        val decodedAccessTokenData = jwtTokenUtils.decodeAccessToken(accessToken)
        val jsonObject = JSONObject(decodedAccessTokenData)
        return TokenModel(
            jsonObject.getLong("id"),
            jsonObject.getString("sub"),
            jsonObject.getBoolean("isAdmin"),
            jsonObject.getInt("exp")
        )
    }
}