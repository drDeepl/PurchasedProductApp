package com.mypurchasedproduct.presentation.state

sealed class UserMainState<T> (val data: T? = null, val message: String? = null){
    class IsSignIn<T> (data: T): UserMainState<T>(data)

    class Loading<T> (message: String?): UserMainState<T>(null, message)

    class Error<T> (message: String, data: T? = null): UserMainState<T>(data, message)


}