package com.mypurchasedproduct.presentation.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.navigation.ScreenNavigation
import com.mypurchasedproduct.presentation.ui.components.LoadScreen


val TAG: String = "CheckLocalAuthScreen"
@Composable
fun CheckLocalAuthScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
)
{
    Log.d(TAG, "START")
    LoadScreen()
    val tokenState = authViewModel.tokenState.collectAsState()
    LaunchedEffect(tokenState.value.isComplete){
        authViewModel.checkAccessToken()
        if(tokenState.value.isComplete){
            val isSignIn = authViewModel.state.value.isSignIn
            var nextRoute = ScreenNavigation.AuthScreenRoute
            if(isSignIn){
                nextRoute = ScreenNavigation.HomeScreenRoute
            }
            navController.navigate(route=nextRoute){
                this.popUpTo(ScreenNavigation.NavHostRoute){
                    inclusive = false
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}
