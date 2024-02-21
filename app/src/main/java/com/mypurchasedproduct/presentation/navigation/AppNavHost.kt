package com.mypurchasedproduct.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.screens.AuthScreen
import com.mypurchasedproduct.presentation.screens.HomeScreen
import com.mypurchasedproduct.presentation.screens.TermsAndConditionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    startDestination: String = ScreenNavigation.AuthScreenRoute,
    modifier:Modifier = Modifier,
){

    Surface(modifier= Modifier.fillMaxSize(), color = Color.White) {
        NavHost(navController = navController , startDestination = ScreenNavigation.AuthScreenRoute) {
            composable(route = ScreenNavigation.AuthScreenRoute){
                AuthScreen(authViewModel=authViewModel,navController=navController)
            }
            composable(route=ScreenNavigation.HomeScreenRoute){

                HomeScreen(authViewModel=authViewModel,screenNavController=navController)


            }
            composable(route=ScreenNavigation.TermsAndCondition){
                TermsAndConditionScreen()
            }

        }
    }
}