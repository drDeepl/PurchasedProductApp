package com.mypurchasedproduct.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mypurchasedproduct.R
import com.mypurchasedproduct.app.PurchasedProductApp
import com.mypurchasedproduct.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.navigation.Screen
import com.mypurchasedproduct.navigation.SystemBackButtonHandler
import com.mypurchasedproduct.ui.components.HeadingTextComponent
import com.mypurchasedproduct.ui.theme.BackgroundColor

@Composable
fun TermsAndConditionScreen() {
    Surface(modifier= Modifier
        .fillMaxSize()
        .background(color = BackgroundColor)
        .padding(16.dp)) {
        HeadingTextComponent(value = stringResource(id = R.string.terms_and_conditions_header))

        SystemBackButtonHandler {
            PurchasedProductAppRouter.navigateTo(Screen.SignUpScreen)
        }

    }
}

@Preview
@Composable
fun TermsAndConditionsPreview(){
    TermsAndConditionScreen()
}