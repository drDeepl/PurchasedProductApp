package com.mypurchasedproduct.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mypurchasedproduct.data.remote.model.response.TokenResponse
import kotlinx.coroutines.flow.map


class DataStoreManager(val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")
    suspend fun saveAccessToken(accessToken: String){
        context.dataStore.edit {pref ->
            pref[stringPreferencesKey("access_token")] = accessToken
        }

    }

    suspend fun saveRefreshToken(refreshToken: String){
        context.dataStore.edit {pref ->
            pref[stringPreferencesKey("refresh_token")] = refreshToken
        }
    }

    fun getAccessToken() = context.dataStore.data.map { pref ->
          pref[stringPreferencesKey("access_token")]
    }

    fun getRefreshToken() = context.dataStore.data.map { pref ->
        pref[stringPreferencesKey("refresh_token")]
    }

    suspend fun removeAccessToken(){
        context.dataStore.edit {pref ->
            pref.remove(stringPreferencesKey("access_token"))
        }
    }

    suspend fun removeRefreshToken(){
        context.dataStore.edit {pref ->
            pref.remove(stringPreferencesKey("refresh_token"))
        }
    }

}