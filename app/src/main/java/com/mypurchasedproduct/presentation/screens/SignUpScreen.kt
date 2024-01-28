package com.mypurchasedproduct.presentation.screens

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
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.data.remote.model.request.SignUpRequest
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.ViewModel.SignInViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.SignUpViewModel
import com.mypurchasedproduct.presentation.ui.components.PrimaryButtonComponent
import com.mypurchasedproduct.presentation.ui.components.CheckBoxComponent
import com.mypurchasedproduct.presentation.ui.components.ClickableTextLogInComponent
import com.mypurchasedproduct.presentation.ui.components.DeviderTextComponent
import com.mypurchasedproduct.presentation.ui.components.ErrorTextComponent
import com.mypurchasedproduct.presentation.ui.components.HeadingTextComponent
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.MyOutlinedTextField
import com.mypurchasedproduct.presentation.ui.components.MyOutlinedTextFieldPassword
import com.mypurchasedproduct.presentation.ui.theme.SecondaryColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes


@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = viewModel(),
    signInViewModel: SignInViewModel = viewModel(),
    appRouter: PurchasedProductAppRouter = PurchasedProductAppRouter,
) {

    val signUpState = signUpViewModel.state
    val signInState = signInViewModel.state
    val passwordValue = remember {
        mutableStateOf("")
    }
    val usernameValue = remember {
        mutableStateOf("")
    }


    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)

    ) {
        if(signUpState.isLoading){
            LoadScreen(isActive = signUpState.isLoading)
        }
        Column(
            modifier=Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start

        ){
            HeadingTextComponent(value = stringResource(id = R.string.create_account), textAlign= TextAlign.Start)
        }
        Column(
            modifier=Modifier.fillMaxSize(),
            verticalArrangement= Arrangement.Center
            ){
            Card(
                modifier= Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                border= BorderStroke(1.dp, SecondaryColor),
                shape = componentShapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,

                ),
            ) {
                if(signUpState.isSignUpSuccess){
                    signInViewModel.toSignIn(usernameValue.value, passwordValue.value)
                    signUpViewModel.defaultState()
                }
                if(signInState.isSignInSuccess){
                    appRouter.navigateTo(Screen.HomeScreen)
                    signInViewModel.defaultState()

                }
                else{
                    Spacer(modifier = Modifier.height(20.dp))
                    if (signUpState.error != null) {
                        Spacer(modifier = Modifier.height(20.dp))
                        ErrorTextComponent(signUpState.error)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        MyOutlinedTextField(
                            usernameValue,
                            labelValue = "Имя пользователя",
                            painterResource(id = R.drawable.user_icon),
                            KeyboardOptions(imeAction = ImeAction.Next),
                            enabled = !signUpState.isLoading
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        MyOutlinedTextFieldPassword(
                            passwordValue = passwordValue,
                            labelValue = "Пароль",
                            painterResource(id = R.drawable.password_icon),
                            KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Go
                            ),
                            enabled = !signUpState.isLoading
                        )
                        CheckBoxComponent(
                            checkedState = signUpState.isApplyTermsAndConditions,
                            textValue = stringResource(id = R.string.term_and_conditions),
                            onTextSelected = {
                                appRouter.navigateTo(Screen.TermsAndConditionsScreen)
                            },
                            onChackedChange = {
                                signUpViewModel.setIsApplyTermsAndConditions(it)
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        PrimaryButtonComponent(
                            value = stringResource(R.string.signup_btn_text),
                            onClickButton = {
                                if (signUpState.isApplyTermsAndConditions) {
                                    signUpViewModel.setError(null)
                                    signUpViewModel.toSignUp(
                                        SignUpRequest(
                                            usernameValue.value,
                                            passwordValue.value
                                        )
                                    )
                                } else {
                                    signUpViewModel.setError("Для продолжения необходимо подтвердить правила пользования")
                                }
                            },
                            isLoading = signUpState.isLoading
                        )


                        Spacer(modifier = Modifier.height(20.dp))
                        DeviderTextComponent("или")
                        ClickableTextLogInComponent(
                            onTextSelected = { appRouter.navigateTo(Screen.SignInScreen) })
                    }

                }
            }

        }

    }
}

//@Preview
//@Composable
//fun DefaultPreviewOfSignUpScreen(signUpViewModel: SignUpViewModel){
//    SignUpScreen(signUpViewModel);
//}