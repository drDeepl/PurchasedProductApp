package com.mypurchasedproduct.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mypurchasedproduct.R
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.navigation.SystemBackButtonHandler
import com.mypurchasedproduct.presentation.ui.components.ButtonComponent
import com.mypurchasedproduct.presentation.ui.components.HeadingTextComponent
import com.mypurchasedproduct.presentation.ui.components.MyTextField
import com.mypurchasedproduct.presentation.ui.components.MyTextFieldPassword
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.theme.SecondaryColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes

@Composable
fun LogInScreen() {


    var usernameValue = remember {
        mutableStateOf("")
    }

    var passwordValue = remember {
        mutableStateOf("")
    }

    SystemBackButtonHandler {
        PurchasedProductAppRouter.navigateTo(Screen.SignUpScreen)
    }
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

//            NormalTextComponent(value = stringResource(id = R.string.log_in_header))
//            HeadingTextComponent(value = stringResource(id = R.string.log_in_welcome))
            HeadingTextComponent(value = stringResource(id = R.string.log_in_header))
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                border = BorderStroke(1.dp, SecondaryColor),
                shape = componentShapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,

                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painter = painterResource(id = R.drawable.user_circle_icon), contentDescription = "", modifier = Modifier.height(128.dp))
                    MyTextField(
                        usernameValue,
                        labelValue = "Имя пользователя",
                        painterResource(id = R.drawable.user_icon),
                        KeyboardOptions(imeAction = ImeAction.Next)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    MyTextFieldPassword(
                        passwordValue,
                        labelValue = "Пароль",
                        painterResource(id = R.drawable.password_icon),
                        KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Go
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonComponent(stringResource(R.string.login_btn_text), {})
                }
            }

        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfLogInScreen(){
    LogInScreen();
}