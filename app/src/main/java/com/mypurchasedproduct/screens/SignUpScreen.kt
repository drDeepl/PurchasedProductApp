package com.mypurchasedproduct.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mypurchasedproduct.R
import com.mypurchasedproduct.ui.components.HeadingTextComponent
import com.mypurchasedproduct.ui.components.MyTextField
import com.mypurchasedproduct.ui.components.NormalTextComponent


@Composable
fun SignUpScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)

    ) {
        Column(modifier=Modifier.fillMaxSize()){
            NormalTextComponent(value= stringResource(id= R.string.hello))
            HeadingTextComponent(value = stringResource(id = R.string.create_account))
            MyTextField(labelValue = "Имя пользователя", painterResource(id = R.drawable.user_icon))
            MyTextField(labelValue = "Пароль", painterResource(id = R.drawable.password_icon))
        }

    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen(){
    SignUpScreen();
}