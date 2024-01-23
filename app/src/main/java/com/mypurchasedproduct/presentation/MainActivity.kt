package com.mypurchasedproduct.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.mypurchasedproduct.data.local.DataStoreManager
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.SignInScreen
import com.mypurchasedproduct.presentation.screens.HomeScreen
import com.mypurchasedproduct.presentation.screens.SignUpScreen
import com.mypurchasedproduct.presentation.screens.ViewModel.SignUpViewModel
import com.mypurchasedproduct.presentation.screens.TermsAndConditionScreen
import com.mypurchasedproduct.presentation.screens.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.SignInViewModel
import com.mypurchasedproduct.presentation.ui.theme.MyPurchasedProductTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val signInViewModel: SignInViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private val TAG: String = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPurchasedProductTheme {
                Surface(modifier= Modifier.fillMaxSize(), color = Color.White) {
                    Crossfade(targetState = PurchasedProductAppRouter.currentScreen) { currentState ->
                        when(currentState.value){
                            is Screen.SignUpScreen ->{
                                SignUpScreen(signUpViewModel, signInViewModel)
                            }
                            is Screen.TermsAndConditionsScreen ->{
                                TermsAndConditionScreen()
                            }
                            is Screen.SignInScreen ->{
                                SignInScreen(signInViewModel)
                            }
                            is Screen.HomeScreen ->{
                                HomeScreen(homeViewModel)
                            }
                        }

                    }

                }
            }
        }
    }
}

