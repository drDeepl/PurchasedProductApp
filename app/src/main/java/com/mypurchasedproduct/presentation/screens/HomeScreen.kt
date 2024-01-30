package com.mypurchasedproduct.presentation.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.ViewModel.AddProductViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.AddPurchasedProductViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ui.components.DialogCardComponent
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.MeasurementUnitDropDownMenuComponent
import com.mypurchasedproduct.presentation.ui.components.MyTextField
import com.mypurchasedproduct.presentation.ui.components.MyTextFieldClickable
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryButtonComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryFloatingActionButton
import com.mypurchasedproduct.presentation.ui.components.ProductsModalBottomSheet
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductViewComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appRouter: PurchasedProductAppRouter = PurchasedProductAppRouter,
    homeViewModel: HomeViewModel = viewModel(),
    addPurchasedProductViewModel: AddPurchasedProductViewModel = viewModel(),
    addProductViewModel: AddProductViewModel = viewModel()
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
        homeViewModel.defaultHomeState()
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
                val addPurchasedProductState = addPurchasedProductViewModel.addPurchasedProductState
                val purchasedProducts: List<PurchasedProductResponse> = getPurchasedProductsState.purchasedProducts
                Scaffold(
                    content = {paddingValues: PaddingValues -> PurchasedProductViewComponent(purchasedProducts, paddingValues=paddingValues)  },
                    floatingActionButton = {
                        PrimaryFloatingActionButton(
                            painter = painterResource(id = R.drawable.ic_plus),
                            onClick={addPurchasedProductViewModel.onAddPurchasedProductClick()})
                    },
                    bottomBar = {}
                )
                if(addPurchasedProductState.isActive){
                    val findProductsState = addProductViewModel.getProductsState
                    val findMeasurementUnitsState = homeViewModel.findMeasurementUnits

                    val products = findProductsState.products
                    val measurementUnits = findMeasurementUnitsState.measurementUnits


                    var productId by remember {mutableStateOf(0)}
                    var count by remember {mutableStateOf("")}
                    var unitMeasurement by remember {mutableStateOf(1)}
                    var price by remember {mutableStateOf("0.0")}

                    if(findProductsState.isUpdating || findMeasurementUnitsState.isUpdating){
                        LoadScreen(isActive = findProductsState.isUpdating)
                        addProductViewModel.getProducts()
                        homeViewModel.getMeasurementUnits()
                    }
                    else if(products != null && measurementUnits != null){
                        val isActiveAddProduct = addProductViewModel.addProductState.isActive
                        val isActiveSelectProduct = addPurchasedProductViewModel.productsBottomSheetState.isActive
                        DialogCardComponent(
                            onConfirm = { addPurchasedProductViewModel.onAddPurchasedProductClick() },
                            onDismiss = {addPurchasedProductViewModel.onCloseAddPurchasedproduct()})
                        {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                // TODO("ADD REQEUST TO ADD PURCHASED PRODUCT && GET SELECTED PRODUCT")
                                MyTextFieldClickable(
                                    selectedValue="выбери продукт",
                                    isExpanded = isActiveSelectProduct,
                                    onClick = { addPurchasedProductViewModel.setOpenProductsBottomSheet(it) })
                                ProductsModalBottomSheet(
                                    products,
                                    isActiveSelectProduct,
                                    {addPurchasedProductViewModel.setOpenProductsBottomSheet(it)},
                                    {addProductViewModel.onClickAddProduct()})
                                MyTextField(textValue = count, labelValue="количество", onValueChange = {count = it},  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), )
                                MeasurementUnitDropDownMenuComponent(measurementUnits)
                                MyTextField(textValue = price, labelValue="цена", onValueChange = {price = it}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                                if(isActiveAddProduct){

                                    DialogCardComponent(
                                        onDismiss = { addProductViewModel.onClickCloseAddProduct()},
                                        onConfirm = { /*TODO*/ }) {
                                        MyTextField(textValue = price, labelValue="цена", onValueChange = {price = it}, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
                                    }
                                }
                            }
                        }
                    }
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
