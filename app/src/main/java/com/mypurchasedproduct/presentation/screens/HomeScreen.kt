package com.mypurchasedproduct.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    val userTokenState = homeViewModel.userTokenState
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(userTokenState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.height(35.dp),
                color = Color.Black
            )
        }
        else{
            val userTokenData = userTokenState.accessTokenData
            NormalTextComponent(value = userTokenData?.id.toString())
            Spacer(modifier = Modifier.padding(10.dp))
            NormalTextComponent(value = userTokenData?.sub.toString())
            Spacer(modifier = Modifier.padding(10.dp))
            NormalTextComponent(value = userTokenData?.isAdmin.toString())
            Spacer(modifier = Modifier.padding(10.dp))
            NormalTextComponent(value = userTokenData?.exp.toString())
        }

    }
}



