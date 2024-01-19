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
import com.mypurchasedproduct.app.PurchasedProductApp
import com.mypurchasedproduct.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.navigation.Screen
import com.mypurchasedproduct.screens.LogInScreen
import com.mypurchasedproduct.screens.MainScreen
import com.mypurchasedproduct.screens.SignUpScreen
import com.mypurchasedproduct.screens.TermsAndConditionScreen
import com.mypurchasedproduct.ui.theme.MyPurchasedProductTheme


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

