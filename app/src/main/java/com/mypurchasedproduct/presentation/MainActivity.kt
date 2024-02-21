package com.mypurchasedproduct.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.navigation.AppNavHost
import com.mypurchasedproduct.presentation.navigation.ScreenNavigation
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
            val navController = rememberNavController()
            val authState = authViewModel.state.collectAsState()
            LaunchedEffect(authState.value.isSignIn){
                Log.wtf(TAG, "LAUNCHED EFFECT")
                if(authState.value.isSignIn){
//                    PurchasedProductAppRouter.navigateTo(Screen.HomeScreen)
                    navController.navigate(route=ScreenNavigation.HomeScreenRoute)
                }
                else{
                    navController.navigate(route=ScreenNavigation.AuthScreenRoute)
//                    PurchasedProductAppRouter.navigateTo(Screen.AuthScreen)
                }
            }
            MyPurchasedProductTheme {
                AppNavHost(navController = navController, authViewModel = authViewModel)
//                    Crossfade(
//                        targetState = PurchasedProductAppRouter.currentScreen, label = "",
//                    ) { currentState ->
//                        when(currentState.value){
//                            is Screen.TermsAndConditionsScreen ->{
//                                TermsAndConditionScreen()
//                            }
//                            is Screen.HomeScreen ->{
//                                HomeScreen(authViewModel=authViewModel)
//                            }
//                            is Screen.AuthScreen ->{
//                                AuthScreen(authViewModel)
//                            }
//                        }
//
//                    }
            }
        }
    }
}

