package com.mypurchasedproduct.presentation.utils

import android.os.Build
import java.util.Base64

class JwtTokenUtils {

    fun decodeAccessToken(jwt: String): String {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val header = String(Base64.getUrlDecoder().decode(parts[0].toByteArray(charset)), charset)
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            return payload
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }
}