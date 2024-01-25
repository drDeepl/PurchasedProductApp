package com.mypurchasedproduct.presentation.screens


import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductItem
import com.mypurchasedproduct.presentation.ui.item.PurchasedProductItem



val cardList: List<PurchasedProductItem> = listOf(
    PurchasedProductItem(
        id=1,
        categoryId = 1,
        productName="Хлеб",
        count=2,
        unitMeasurement="шт.",
        price= 56.0,
    ),
    PurchasedProductItem(
        id=2,
        categoryId = 1,
        productName="Мандарины",
        count=5,
        unitMeasurement="шт.",
        price= 325.0,
    ),
    PurchasedProductItem(
        id=3,
        categoryId = 1,
        productName="Сыр",
        count=5,
        unitMeasurement="г.",
        price= 412.0,
    )
)

@Composable
fun HomeScreen(
    appRouter: PurchasedProductAppRouter,
    homeViewModel: HomeViewModel) {
    val screenState = homeViewModel.state
    val userTokenState = homeViewModel.userTokenState
    if(!screenState.isSignIn){
        homeViewModel.checkAccessToken()
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(userTokenState.accessToken == null && !screenState.isLoading){
            PurchasedProductAppRouter.navigateTo(Screen.SignUpScreen)
        }
        if(screenState.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.height(35.dp),
                color = Color.Black
            )
        }
        else{
            val getPurchasedProductsState = homeViewModel.getPurchasedProductsState
            if(getPurchasedProductsState.isSuccessResponse){
                val purchasedProducts: List<PurchasedProductResponse> = getPurchasedProductsState.responseData
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
                        items(purchasedProducts){purchasedProduct ->
                            PurchasedProductItem(purchasedProduct)
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun HomeScreenTest(){


}

@Preview
@Composable
fun HomeScreenTestPreview(){
    HomeScreenTest()
}
