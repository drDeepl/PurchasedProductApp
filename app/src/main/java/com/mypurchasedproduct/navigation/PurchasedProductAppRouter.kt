package com.mypurchasedproduct.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    object SignUpScreen: Screen()
    object TermsAndConditionsScreen: Screen()
    object LogInScreen: Screen()

}

object PurchasedProductAppRouter{
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignUpScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination

    }
}