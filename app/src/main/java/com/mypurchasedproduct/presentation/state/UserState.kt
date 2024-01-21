package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.UserDataResponse

data class UserState(
    val isLoading: Boolean = false,
    val accessToken: String? = null,
    val userData: UserDataResponse? = null
)
