package com.mypurchasedproduct.presentation.screens

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
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.navigation.SystemBackButtonHandler
import com.mypurchasedproduct.presentation.ui.components.HeadingTextComponent
import com.mypurchasedproduct.presentation.ui.theme.BackgroundColor

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