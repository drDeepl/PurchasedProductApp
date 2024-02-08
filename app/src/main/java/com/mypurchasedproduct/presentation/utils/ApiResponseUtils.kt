package com.mypurchasedproduct.presentation.utils

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Response
import org.json.JSONObject

object ApiResponseUtils {

    private val TAG: String = this.javaClass.simpleName

    fun parseMessageFromErrorBody(responseBody: ResponseBody?): String
    {
        Log.i(TAG,"PARSE MESSAGE FROM ERROR BODY")
        responseBody?.let{
            val jsonObject = JSONObject(it.string())
            return jsonObject.getString("message")
        } ?: return "упс.. что-то пошло не так"
    }
}