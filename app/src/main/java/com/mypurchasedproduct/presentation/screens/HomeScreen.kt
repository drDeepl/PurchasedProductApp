package com.mypurchasedproduct.presentation.screens


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.screens.ViewModel.AddProductViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.AddPurchasedProductViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.screens.ViewModel.PurchasedProductListViewModel
import com.mypurchasedproduct.presentation.ui.components.AlertDialogComponent
import com.mypurchasedproduct.presentation.ui.components.DialogCardComponent
import com.mypurchasedproduct.presentation.ui.components.ErrorMessageDialog
import com.mypurchasedproduct.presentation.ui.components.HeadingTextComponent
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.MeasurementUnitsScrollableRow
import com.mypurchasedproduct.presentation.ui.components.MyTextField
import com.mypurchasedproduct.presentation.ui.components.MyTextFieldClickable
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryButtonComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryFloatingActionButton
import com.mypurchasedproduct.presentation.ui.components.ProductsModalBottomSheet
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductViewComponent
import com.mypurchasedproduct.presentation.ui.components.SelectCategoryButton
import com.mypurchasedproduct.presentation.ui.components.SuccessMessageDialog


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appRouter: PurchasedProductAppRouter = PurchasedProductAppRouter,
    homeViewModel: HomeViewModel = viewModel(),
    addPurchasedProductViewModel: AddPurchasedProductViewModel = viewModel(),
    addProductViewModel: AddProductViewModel = viewModel(),
    purchasedProductListVM: PurchasedProductListViewModel = viewModel()
) {
    val homeState = homeViewModel.state
    Log.e("HOME SCREEN", "START HOME SCREEN IS SIGN IN: ${homeState.isSignIn}")
    if(homeState.isLoading){
        LoadScreen()
    }

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
            PrimaryButtonComponent(
                value = "Выйти", onClickButton = {
                homeViewModel.signOut()
                appRouter.navigateTo(Screen.SignUpScreen)
            })
            val getPurchasedProductsByDateState = purchasedProductListVM.getPurchasedProductsByDateState

            if(getPurchasedProductsByDateState.isActive){
                purchasedProductListVM.getPurchasedProductCurrentUserByDate()
                LoadScreen()
            }
            else if(getPurchasedProductsByDateState.isSuccessResponse){
                val deletePurchasedProductState = purchasedProductListVM.deletePurchasedProductState
                val addPurchasedProductState = addPurchasedProductViewModel.addPurchasedProductState
                val purchasedProducts: List<PurchasedProductResponse> = getPurchasedProductsByDateState.purchasedProducts
                val totalCosts = purchasedProductListVM.totalCosts
                HeadingTextComponent(value = "Потрачено сегодня: ${totalCosts} ₽")

                if(deletePurchasedProductState.isActive){
                    AlertDialogComponent(
                        headerText="Удалить купленный продукт?",
                        onDismiss = {purchasedProductListVM.onDismissDeletePurchasedProduct()},
                        onConfirm = {purchasedProductListVM.deletePurchasedProduct()},
                    )
                    {
                        NormalTextComponent(value = "Будет удалено: ${deletePurchasedProductState?.purchasedProduct?.productName}")
                    }
                    if(deletePurchasedProductState.isSuccess){
                        SuccessMessageDialog(
                            text = "Купленный продукт удален!",
                            onDismiss = {purchasedProductListVM.setDefaultDeletePurchasedProductState()
                            purchasedProductListVM.setUpdatePurchasedProductByDate(true)
                            }
                        )
                    }
                    if(deletePurchasedProductState.isError){
                        ErrorMessageDialog(headerText = "Что-то пошло не так", description =deletePurchasedProductState.error.toString(), onDismiss = {purchasedProductListVM.setDefaultDeletePurchasedProductState()})
                    }
                }
                Scaffold(
                    content = {paddingValues: PaddingValues ->
                        if(getPurchasedProductsByDateState.isLoading){
                            LoadScreen()
                        }
                        else{
                            PurchasedProductViewComponent(purchasedProducts,
                                paddingValues=paddingValues,
                                onSwipeDeletePurchasedProduct={purchasedProductListVM.onSwipeDelete(it)}
                            )
                        }
                              },
                    floatingActionButton = {
                        PrimaryFloatingActionButton(
                            painter = painterResource(id = R.drawable.ic_plus),
                            onClick={addPurchasedProductViewModel.onAddPurchasedProductClick()})
                    },
                    bottomBar = {}
                )
                if (addPurchasedProductState.isLoading) {
                    LoadScreen()
                }
                if(addPurchasedProductState.isSuccess){
                    SuccessMessageDialog(
                        text="Купленный продукт добавлен!",
                        onDismiss = {
                            addPurchasedProductViewModel.setDefaultAddPurchasedProductState()
                            purchasedProductListVM.getPurchasedProductCurrentUserByDate()
                            addPurchasedProductViewModel.setDefaultFormDataAddPurchasedProduct()

                        }

                    )
                }
                else if(addPurchasedProductState.isError){
                    val msg = addPurchasedProductState.error.toString()
                    ErrorMessageDialog(headerText="Что-то пошло не так", description=msg, onDismiss= {
                        addPurchasedProductViewModel.setDefaultAddPurchasedProductState()
                        addPurchasedProductViewModel.setDefaultFormDataAddPurchasedProduct()
                    })
                }
                else if(addPurchasedProductState.isActive) {
                    val findProductsState = addProductViewModel.getProductsState
                    val findMeasurementUnitsState = homeViewModel.findMeasurementUnits
                    val products = findProductsState.products
                    val measurementUnits = findMeasurementUnitsState.measurementUnits

                    val addPurchasedProductData =
                        addPurchasedProductViewModel.addPurchasedProductFormData

                    val productName =
                        if (addPurchasedProductData.product != null) addPurchasedProductData.product.name else "выбери продукт"
                    val currentMeasurementUnitId = addPurchasedProductData.unitMeasurement
                    val count = addPurchasedProductData.count
                    val price = addPurchasedProductData.price
                    if (findProductsState.isUpdating || findMeasurementUnitsState.isUpdating) {
                        LoadScreen()
                        if (findProductsState.isUpdating) {
                            addProductViewModel.getProducts()
                        }
                        if (findMeasurementUnitsState.isUpdating) {
                            homeViewModel.getMeasurementUnits()
                        }
                    } else if (products != null && measurementUnits != null) {
                        val isActiveAddProduct = addProductViewModel.addProductFormState.isActive
                        val isActiveSelectProduct =
                            addPurchasedProductViewModel.productsBottomSheetState.isActive
                        DialogCardComponent(
                            onConfirm = { addPurchasedProductViewModel.onClickSavePurchasedProduct() },
                            onDismiss = { addPurchasedProductViewModel.onCloseAddPurchasedproduct() })
                        {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MyTextFieldClickable(
                                    selectedValue = productName,
                                    isExpanded = isActiveSelectProduct,
                                    onClick = {
                                        addPurchasedProductViewModel.setOpenProductsBottomSheet(
                                            it
                                        )
                                    })
                                ProductsModalBottomSheet(
                                    products = products,
                                    openBottomSheet = isActiveSelectProduct,
                                    setStateButtomSheet = {
                                        addPurchasedProductViewModel.setOpenProductsBottomSheet(
                                            it
                                        )
                                    },
                                    onClickAddProduct = { addProductViewModel.onClickAddProduct(); },
                                    onClickProductItem = {
                                        addPurchasedProductViewModel.setProductFormData(it)
                                        addPurchasedProductViewModel.setOpenProductsBottomSheet(false)
                                    }
                                )
                                MyTextField(
                                    textValue = count,
                                    labelValue = "количество",
                                    onValueChange = {
                                        addPurchasedProductViewModel.setCountFormData(it)
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                )
                                MeasurementUnitsScrollableRow(
                                    measurementUnits = measurementUnits,
                                    onClickButton = {
                                        addPurchasedProductViewModel.setMeasurementUnitId(
                                            it
                                        )
                                    },
                                    selectedUnitId = currentMeasurementUnitId
                                )
                                MyTextField(
                                    textValue = price,
                                    labelValue = "цена",
                                    onValueChange = {
                                        addPurchasedProductViewModel.setPriceFormData(it)
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                                if (isActiveAddProduct) {
                                    val getCategoriesState = addProductViewModel.getCategoriesState
                                    val categories = getCategoriesState.categories
                                    if (categories == null) {
                                        addProductViewModel.getCategories()
                                    }
                                    val productItem = addProductViewModel.productItem
                                    val addProductState = addProductViewModel.addProductState
                                    if (getCategoriesState.isLoading) {
//                                        TODO("LOAD BAR IF IS LOADING")
                                        LoadScreen()
                                    }
                                    DialogCardComponent(
                                        onDismiss = { addProductViewModel.onClickCloseAddProduct() },
                                        onConfirm = { addProductViewModel.toAddProduct() }
                                    ) {

                                        if (addProductViewModel.addProductState.isError) {
                                            ErrorMessageDialog(
                                                headerText = "Что-то пошло не так",
                                                description = addProductViewModel.addProductState.error.toString(),
                                                onDismiss = {addProductViewModel.setDefaultAddProductState()}
                                            )
                                        }else if (addProductState.isSuccess) {
                                            SuccessMessageDialog(
                                                text = "Продукт добавлен!",
                                                onDismiss = {
                                                    addProductViewModel.setDefaultProductItem()
                                                    addProductViewModel.setDefaultAddProductState()
                                                    addProductViewModel.getProducts()
                                                }
                                            )
                                        } else {
                                            Row() {
                                                categories?.let { listCategories ->
                                                    listCategories.forEach { categoryResponse ->
                                                        SelectCategoryButton(
                                                            categoryResponse = categoryResponse,
                                                            onClick = {
                                                                addProductViewModel.setProductCategoryId(
                                                                    categoryResponse.id
                                                                )
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                            MyTextField(
                                                textValue = productItem.productName,
                                                labelValue = "продукт",
                                                onValueChange = {
                                                    addProductViewModel.setProductName(it)
                                                },
                                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                            )
                                        }
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
