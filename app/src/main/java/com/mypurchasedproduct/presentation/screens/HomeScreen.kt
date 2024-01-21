package com.mypurchasedproduct.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mypurchasedproduct.presentation.screens.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent


val cardList = listOf(
    "Card 1",
    "Card 2",
    "Card 3",
    "Card 4",
    "Card 5"
)

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    val homeState = homeViewModel.

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        NormalTextComponent(value = homeViewModel.accessToken.toString())

    }
}



