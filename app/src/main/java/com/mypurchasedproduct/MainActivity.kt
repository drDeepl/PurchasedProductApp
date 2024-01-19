package com.mypurchasedproduct

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.LogInScreen
import com.mypurchasedproduct.presentation.screens.MainScreen
import com.mypurchasedproduct.presentation.screens.SignUpScreen
import com.mypurchasedproduct.presentation.screens.TermsAndConditionScreen
import com.mypurchasedproduct.presentation.ui.theme.MyPurchasedProductTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPurchasedProductTheme {
                Surface(modifier= Modifier.fillMaxSize(), color = Color.White) {
                    Crossfade(targetState = PurchasedProductAppRouter.currentScreen) { currentState ->
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
        }
    }
}

