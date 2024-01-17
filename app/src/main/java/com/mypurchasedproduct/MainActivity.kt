package com.mypurchasedproduct

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mypurchasedproduct.app.PurchasedProductApp
import com.mypurchasedproduct.screens.MainScreen
import com.mypurchasedproduct.screens.SignUpScreen
import com.mypurchasedproduct.ui.theme.MyPurchasedProductTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPurchasedProductTheme {
                // A surface container using the 'background' color from the theme
//                MainScreen()
                PurchasedProductApp()
            }
        }
    }
}

