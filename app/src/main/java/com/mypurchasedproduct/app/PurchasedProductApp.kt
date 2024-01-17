package com.mypurchasedproduct.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mypurchasedproduct.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.navigation.Screen
import com.mypurchasedproduct.screens.LogInScreen
import com.mypurchasedproduct.screens.SignUpScreen
import com.mypurchasedproduct.screens.TermsAndConditionScreen

@Composable
fun PurchasedProductApp() {
    Surface(modifier= Modifier.fillMaxSize(), color = Color.White) {

        Crossfade(targetState = PurchasedProductAppRouter.currentScreen) {currentState ->
            when(currentState.value){
                is Screen.SignUpScreen ->{
                    SignUpScreen()
                }
                is Screen.TermsAndConditionsScreen ->{
                    TermsAndConditionScreen()
                }
                is Screen.LogInScreen ->{
                    LogInScreen()
                }
            }

        }

    }

}