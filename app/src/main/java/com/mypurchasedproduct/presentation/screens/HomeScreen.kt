package com.mypurchasedproduct.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.screens.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.theme.AcidPurpleColor
import com.mypurchasedproduct.presentation.ui.theme.AcidRedColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes



val cardList = listOf(
    PurchasedProductResponse(
        id=1,
        userId=2,
        categoryId = 1,
        productName="Хлеб",
        count=2,
        unitMeasurement="шт.",
        price= 56.0,
        purchasedDate = System.currentTimeMillis().toString()
    ),
    PurchasedProductResponse(
        id=2,
        userId=2,
        categoryId = 1,
        productName="Мандарины",
        count=5,
        unitMeasurement="шт.",
        price= 325.0,
        purchasedDate = System.currentTimeMillis().toString()
    ),
    PurchasedProductResponse(
        id=3,
        userId=2,
        categoryId = 1,
        productName="Сыр",
        count=5,
        unitMeasurement="г.",
        price= 412.0,
        purchasedDate = System.currentTimeMillis().toString()
    )

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

@Composable
fun HomeScreenTest(){
    TODO("added shadow to row && remove to AppComponents")
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
        ){
            items(cardList){purchasedProduct ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(11.dp)
                        .height(65.dp)
                        .background(Color.White)
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(listOf(AcidRedColor, AcidPurpleColor)),
                            shape= componentShapes.medium
                        )
                )

              {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment= Alignment.Start,
                        verticalArrangement = Arrangement.SpaceAround
                    ){
                        Text(
                            purchasedProduct.productName,
                            fontSize=18.sp,
                            fontWeight= FontWeight.Bold,
                        )
                        Row(horizontalArrangement = Arrangement.Center){
                            Text(
                                purchasedProduct.count.toString(),
                                fontSize=16.sp,
                            )
                            Spacer(modifier = Modifier.padding(2.dp,0.dp))
                            Text(
                                purchasedProduct.unitMeasurement,
                                fontSize=16.sp,
                            )

                        }
                    }
                    Text(
                        "${purchasedProduct.price.toString()} ₽",
                        modifier = Modifier.padding(8.dp),
                        fontSize=17.sp,
                        fontWeight= FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                }

            }
        }
    }


}

@Preview
@Composable
fun HomeScreenTestPreview(){
    HomeScreenTest()
}
