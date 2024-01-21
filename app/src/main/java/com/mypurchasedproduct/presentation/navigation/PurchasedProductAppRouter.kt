package com.mypurchasedproduct.presentation.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    object SignUpScreen: Screen()
    object TermsAndConditionsScreen: Screen()
    object SignInScreen: Screen()
    object HomeScreen: Screen()

}

object PurchasedProductAppRouter{
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.HomeScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination

    }
}