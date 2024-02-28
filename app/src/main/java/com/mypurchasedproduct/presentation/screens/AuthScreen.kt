package com.mypurchasedproduct.presentation.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.ViewModel.SignInViewModel
import com.mypurchasedproduct.presentation.ViewModel.SignUpViewModel
import com.mypurchasedproduct.presentation.navigation.ScreenNavigation
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.SelectionTabComponent
import com.mypurchasedproduct.presentation.ui.components.SignInFormComponent
import com.mypurchasedproduct.presentation.ui.components.SignUpFormComponent
import com.mypurchasedproduct.presentation.ui.theme.SecondaryColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes


@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController,

){
    Log.wtf("AuthScreen", "START")
    val authState = authViewModel.state.collectAsState()
    val signInState = signInViewModel.signInState.collectAsState()
    val signUpState = signUpViewModel.signUpState.collectAsState()
    val scope = rememberCoroutineScope()
    LoadScreen(isActive=authState.value.isLoading)
    LaunchedEffect(signUpState.value.isSignUpSuccess){
        Log.wtf("AuthScreen", "SIGN IN STATE ${signInState.value.isSuccess}")
        if(signUpState.value.isSignUpSuccess){
            signInViewModel.onUsernameChange(signUpState.value.username)
            signInViewModel.onPasswordChange(signUpState.value.password)
            signInViewModel.toSignIn()
            signUpViewModel.setDefaultState()
            authViewModel.setLoading(false)
        }

    }
    LaunchedEffect(signInState.value.isSuccess){
        Log.wtf("AuthScreen", "SIGN IN STATE ${signInState.value.isSuccess}")
        if(signInState.value.isSuccess){
            authViewModel.setSignIn(signInState.value.isSuccess)
            navController.navigate(route= ScreenNavigation.HomeScreenRoute){
                this.popUpTo(ScreenNavigation.NavHostRoute){
                    inclusive = false
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            signInViewModel.defaultState()
            authViewModel.setCurrentAction(0)
        }
    }

    val tabs = authViewModel.actionTabs
    val currentTab = authViewModel.currentTab.collectAsState()


    Box(
        modifier= Modifier
            .fillMaxSize()
            ,
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .border(border = BorderStroke(1.dp, SecondaryColor), shape = componentShapes.large)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
            )
        {
            Spacer(modifier= Modifier.heightIn(16.dp))
            SelectionTabComponent(
                labelTabs = tabs,
                currentTab = currentTab,
                onClickTab = {
                    authViewModel.setCurrentAction(it)
                }
            )
            Box(
                modifier = Modifier.animateContentSize()
            )
            {
                when (currentTab.value) {
                    "вход" -> {
                        SignInFormComponent(viewModel = signInViewModel)
                    }

                    "регистрация" -> {
                        SignUpFormComponent(
                            signUpViewModel,
                            onTermsAndConditionText = {
//                                appRouter.navigateTo(Screen.TermsAndConditionsScreen)
                                navController.navigate(route=ScreenNavigation.TermsAndConditionRoute)
                            }
                        )
                    }
                }
            }
            Spacer(modifier= Modifier.heightIn(16.dp))
        }
    }
}