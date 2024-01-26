package com.mypurchasedproduct.presentation.screens


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ui.components.CustomDeialog
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryButtonComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryFloatingActionButton
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductItem
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductViewComponent
import com.mypurchasedproduct.presentation.ui.item.PurchasedProductItem
import com.mypurchasedproduct.presentation.ui.theme.AcidPurpleColor
import com.mypurchasedproduct.presentation.ui.theme.AcidRedColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appRouter: PurchasedProductAppRouter = PurchasedProductAppRouter,
    homeViewModel: HomeViewModel = viewModel()
) {
    val purchasedProductPerPage: Int = 5
    val homeState = homeViewModel.state
    val checkTokenState = homeViewModel.checkTokenState
    Log.e("HOME SCREEN", "START HOME SCREEN IS SIGN IN: ${homeState.isSignIn}")
    LoadScreen(isActive=homeState.isLoading)
    if(homeState.isSignIn == null){
        homeViewModel.checkAccessToken()
    }
    else if(!homeState.isSignIn){
        appRouter.navigateTo(Screen.SignUpScreen)
    }
    else{
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PrimaryButtonComponent(value = "Выйти", onClickButton = {
                homeViewModel.signOut()
                appRouter.navigateTo(Screen.SignUpScreen)
            })
            val getPurchasedProductsState = homeViewModel.getPurchasedProductsState
            if(getPurchasedProductsState.isActive){
                homeViewModel.getPurchasedProductCurrentUser(purchasedProductPerPage)
                LoadScreen(isActive=getPurchasedProductsState.isActive)
            }
            else if(getPurchasedProductsState.isSuccessResponse){
                val addPurchasedProductState = homeViewModel.addPurchasedProductState
                val purchasedProducts: List<PurchasedProductResponse> = getPurchasedProductsState.purchasedProducts
                Scaffold(
                    content = {paddingValues: PaddingValues -> PurchasedProductViewComponent(purchasedProducts, paddingValues=paddingValues)  },
                    floatingActionButton = {
                        PrimaryFloatingActionButton(
                            painter = painterResource(id = R.drawable.ic_plus),
                            onClick={homeViewModel.onAddPurchasedProductClick()})
                    }
                )
                if(addPurchasedProductState.isActive){
//                    TODO("ADD REQEUST TO ADD PURCHASED PRODUCT")
                    CustomDeialog(onConfirm = { homeViewModel.onAddPurchasedProductClick() }, onDismiss = {homeViewModel.onCloseAddPurchasedproduct()})
                }

            }
            else{
                NormalTextComponent(value = "Произошла ошибка при получении покупок")
            }
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()

}
