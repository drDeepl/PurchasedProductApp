package com.mypurchasedproduct.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.AuthScreen
import com.mypurchasedproduct.presentation.screens.HomeScreen
import com.mypurchasedproduct.presentation.screens.TermsAndConditionScreen
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.theme.MyPurchasedProductTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    private val TAG: String = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.wtf(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContent {
            val authState = authViewModel.state.collectAsState()
            LaunchedEffect(authState.value.isSignIn){
                Log.wtf(TAG, "LAUCNHED EFFECT")
                if(authState.value.isSignIn){
                    PurchasedProductAppRouter.navigateTo(Screen.HomeScreen)
                }
                else{
                    PurchasedProductAppRouter.navigateTo(Screen.AuthScreen)
                }
            }
            MyPurchasedProductTheme {
                Surface(modifier= Modifier.fillMaxSize(), color = Color.White) {
                    Crossfade(targetState = PurchasedProductAppRouter.currentScreen, label = "",
                    ) { currentState ->
                        when(currentState.value){
                            is Screen.LoadScreen ->{
                                LoadScreen()
                            }
                            is Screen.TermsAndConditionsScreen ->{
                                TermsAndConditionScreen()
                            }
                            is Screen.HomeScreen ->{
                                HomeScreen(authViewModel=authViewModel)
                            }
                            is Screen.AuthScreen ->{
                                AuthScreen(authViewModel)
                            }
                        }

                    }

                }
            }
        }
    }
}

