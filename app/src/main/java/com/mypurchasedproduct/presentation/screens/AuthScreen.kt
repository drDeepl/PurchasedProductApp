package com.mypurchasedproduct.presentation.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.SignInViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.SignUpViewModel
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.SelectionTabComponent
import com.mypurchasedproduct.presentation.ui.components.SignInFormComponent
import com.mypurchasedproduct.presentation.ui.components.SignUpFormComponent
import com.mypurchasedproduct.presentation.ui.components.WithAnimation
import com.mypurchasedproduct.presentation.ui.theme.SecondaryColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes


@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = viewModel(),
    signUpViewModel: SignUpViewModel = viewModel(),
    signInViewModel: SignInViewModel = viewModel(),
    appRouter: PurchasedProductAppRouter = PurchasedProductAppRouter,
){
    Log.wtf("AuthScreen", "START")
    val authState = authViewModel.state.collectAsState()
    val tokenState = authViewModel.tokenState.collectAsState()
    val signInState = authViewModel.signInState.collectAsState()
//    LoadScreen(isActive = authState.value.isLoading)
//    LaunchedEffect(signInState.value.isSuccess){
//        Log.wtf("AuthScreen", "LAUNCHED EFFECT")
//        authViewModel.checkAccessToken()
//    }
    if(authState.value.isSignIn){
        appRouter.navigateTo(Screen.HomeScreen)
    }
    val tabs = authViewModel.actionTabs
    val currentTab = authViewModel.currentTab.collectAsState()
    val animated = authViewModel.animated.value

    val rotation = remember { Animatable(initialValue = 360f) }

    LaunchedEffect(animated) {
        rotation.animateTo(
            targetValue = if (animated) 0f else 360f,
            animationSpec = tween(durationMillis = 1000),
        )
    }
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
                currentTab = currentTab.value,
                onClickTab = { authViewModel.setCurrentAction(it) }
            )
            Box(
                modifier = Modifier.graphicsLayer {
                    rotationY = rotation.value
                }
            )
            {
                when (currentTab.value) {
                    "вход" -> {
//                    Icon(painter = painterResource(id = R.drawable.user_circle_icon), contentDescription = "", modifier = Modifier.height(128.dp))
                        SignInFormComponent(viewModel = signInViewModel)
                    }

                    "регистрация" -> {
                        SignUpFormComponent(
                            signUpViewModel,
                            onTermsAndConditionText = {
                                appRouter.navigateTo(Screen.TermsAndConditionsScreen)
                            }
                        )
                    }
                }
            }
            Spacer(modifier= Modifier.heightIn(16.dp))
        }
    }
}