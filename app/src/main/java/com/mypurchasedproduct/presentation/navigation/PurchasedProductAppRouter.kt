package com.mypurchasedproduct.presentation.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {

    object TermsAndConditionsScreen: Screen()
    object HomeScreen: Screen()
    object AuthScreen: Screen()

}

object PurchasedProductAppRouter{
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.AuthScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination

    }
}