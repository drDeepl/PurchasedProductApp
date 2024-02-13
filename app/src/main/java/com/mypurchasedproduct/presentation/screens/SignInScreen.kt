package com.mypurchasedproduct.presentation.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.navigation.SystemBackButtonHandler
import com.mypurchasedproduct.presentation.screens.ViewModel.SignInFormViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.SignInViewModel
import com.mypurchasedproduct.presentation.ui.components.ErrorTextComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryButtonComponent
import com.mypurchasedproduct.presentation.ui.components.HeadingTextComponent
import com.mypurchasedproduct.presentation.ui.components.MyOutlinedTextField
import com.mypurchasedproduct.presentation.ui.components.MyOutlinedTextFieldPassword
import com.mypurchasedproduct.presentation.ui.components.SignInFormComponent
import com.mypurchasedproduct.presentation.ui.components.WithAnimation
import com.mypurchasedproduct.presentation.ui.theme.SecondaryColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = viewModel(),
    signInFormViewModel: SignInFormViewModel = viewModel(),
    appRouter: PurchasedProductAppRouter = PurchasedProductAppRouter) {
    SystemBackButtonHandler {
        appRouter.navigateTo(Screen.SignUpScreen)
    }
    val signInState = signInViewModel.signInState.collectAsState()
    if(signInState.value.isSuccess){
        appRouter.navigateTo(Screen.HomeScreen)
        signInViewModel.defaultState()
    }
    WithAnimation(animation = scaleIn() + fadeIn()) {
        SignInFormComponent(
            viewModel = signInFormViewModel,
            onConfirm = {username,password -> signInViewModel.toSignIn(username, password)}
            )

    }
}