package com.mypurchasedproduct.presentation.screens


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
import com.mypurchasedproduct.presentation.ui.components.DialogCardComponentWithoutActionBtns
import com.mypurchasedproduct.presentation.ui.components.ErrorMessageDialog
import com.mypurchasedproduct.presentation.ui.components.FormModalBottomSheet
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
import com.mypurchasedproduct.presentation.ui.components.SecondaryButtonComponent
import com.mypurchasedproduct.presentation.ui.components.SelectCategoryButton
import com.mypurchasedproduct.presentation.ui.components.SuccessMessageDialog
import com.mypurchasedproduct.presentation.ui.theme.DeepGreyColor
import kotlinx.coroutines.coroutineScope


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

    if(homeState.isSignIn == null){
        homeViewModel.checkAccessToken()
    }
    else if(!homeState.isSignIn){
        appRouter.navigateTo(Screen.SignUpScreen)
        homeViewModel.defaultHomeState()
    }
    else if(homeState.isLoading){
        LoadScreen()
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
                LoadScreen()
                purchasedProductListVM.getPurchasedProductCurrentUserByDate()

            }
            else if(getPurchasedProductsByDateState.isSuccessResponse){
                val deletePurchasedProductState = purchasedProductListVM.deletePurchasedProductState
                val addPurchasedProductState = addPurchasedProductViewModel.addPurchasedProductState
                val purchasedProducts: List<PurchasedProductResponse> = getPurchasedProductsByDateState.purchasedProducts

                HeadingTextComponent(value = "Потрачено сегодня: ${purchasedProductListVM.totalCosts} ₽")

                if(deletePurchasedProductState.isActive){
                    AlertDialogComponent(
                        headerText="Удалить купленный продукт?",
                        onDismiss = {purchasedProductListVM.onDismissDeletePurchasedProduct()},
                        onConfirm = {purchasedProductListVM.deletePurchasedProduct()},
                    )
                    {
                        NormalTextComponent(value = "Будет удалено: ${deletePurchasedProductState?.purchasedProduct?.product?.name}")
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
                            onClick={
                                addPurchasedProductViewModel.onAddPurchasedProductClick()
                                addProductViewModel.findProducts()
                                addPurchasedProductViewModel.findMeasurementUnits()
                            })
                    },
                    bottomBar = {}
                )
                if (addPurchasedProductState.isLoading) {
                    LoadScreen()
                }
                else if(addPurchasedProductState.isActive) {
                    val products = addProductViewModel.getProductsState.products
                    val measurementUnits = addPurchasedProductViewModel.getMeasurementUnits()
//                    val addPurchasedProductData =
//                        addPurchasedProductViewModel.addPurchasedProductFormData
//                    val productName =
//                        if (addPurchasedProductData.product != null) addPurchasedProductData.product.name else "выбери продукт"
//                    val currentMeasurementUnitId = addPurchasedProductData.unitMeasurement
//                    val count = addPurchasedProductData.count
//                    val price = addPurchasedProductData.price

                    if (addProductViewModel.getProductsState.isUpdating || addPurchasedProductViewModel.findMeasurementUnits.isUpdating) {
                        LoadScreen()
//                        if (addProductViewModel.getProductsState.isUpdating) {
//                            addProductViewModel.getProducts()
//                        }
//                        if (homeViewModel.findMeasurementUnits.isUpdating) {
//                            homeViewModel.getMeasurementUnits()
//                        }
                    }
                    if (addProductViewModel.getProductsState.isSuccess && addPurchasedProductViewModel.findMeasurementUnits.isSuccess) {
                        val isActiveSelectProduct =
                            addPurchasedProductViewModel.productsBottomSheetState.isActive
                        FormModalBottomSheet(
                            openBottomSheet = addPurchasedProductState.isActive,
                            setStateButtomSheet = {
                                addPurchasedProductViewModel.setActiveAddPurchasedProductForm(it)
                            }
                        )
                        {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(border= BorderStroke(width=2.dp, color= DeepGreyColor)),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(products == null){
                                    Text(text="Произошла ошибка при загрузке продуктов. Попробуйте снова позже", fontSize = 12.sp)
                                }
                                else{
                                    var selectedProduct by remember {
                                        mutableStateOf(products[0])
                                    }
                                    var productName by remember {
                                        mutableStateOf("выбери продукт")
                                    }
                                    var count by remember { mutableStateOf("") }
                                    var price by remember {
                                        mutableStateOf("")
                                    }

                                    var currentMeasurementUnitId by remember {
                                        mutableStateOf(1)
                                    }

                                    ProductsModalBottomSheet(
                                            products = products,
                                            openBottomSheet = isActiveSelectProduct,
                                            setStateButtomSheet = {
                                                addPurchasedProductViewModel.setOpenProductsBottomSheet(it)
                                                                  },
                                            onClickAddProduct = {
                                                addProductViewModel.onClickAddProduct()
                                                addProductViewModel.getCategories()
                                                                },
                                            onClickProductItem = {
                                                productName = it.name

//                                                addPurchasedProductViewModel.setProductFormData(it)
                                                selectedProduct = it
                                                addPurchasedProductViewModel.setOpenProductsBottomSheet(false)
                                            }
                                        )

                                        MyTextFieldClickable(
                                            selectedValue = productName,
                                            isExpanded = isActiveSelectProduct,
                                            onClick = {
                                                addPurchasedProductViewModel.setOpenProductsBottomSheet(
                                                    it
                                                )
                                            })
                                    MyTextField(
                                        textValue = count,
                                        labelValue = "количество",
                                        onValueChange = {
//                                            addPurchasedProductViewModel.setCountFormData(it)
                                                        count = it
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    )
                                    MeasurementUnitsScrollableRow(
                                        measurementUnits = measurementUnits,
                                        onClickButton = {
//                                            addPurchasedProductViewModel.setMeasurementUnitId(
//                                                it
//                                            )
                                                        currentMeasurementUnitId = it.toInt()
                                        },
                                        selectedUnitId = currentMeasurementUnitId.toLong()
                                    )
                                    MyTextField(
                                        textValue = price,
                                        labelValue = "цена",
                                        onValueChange = {
//                                            addPurchasedProductViewModel.setPriceFormData(it)
                                                        price = it
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        SecondaryButtonComponent(
                                            value = "добавить",
                                            onClickButton = {addPurchasedProductViewModel.toAddPurchasedProduct(selectedProduct,count,price,currentMeasurementUnitId)},
                                            modifier = Modifier.widthIn(150.dp)
                                        )
                                        SecondaryButtonComponent(
                                            value = "отмена",
                                            onClickButton = {addPurchasedProductViewModel.onCloseAddPurchasedProduct()},
                                            modifier = Modifier.widthIn(150.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (addProductViewModel.addProductFormState.isActive) {
                        val getCategoriesState = addProductViewModel.getCategoriesState
//                        val productItem = addProductViewModel.productItem
                        var addedProductName by remember {
                            mutableStateOf("")
                        }
                        val addProductState = addProductViewModel.addProductState
                        if (getCategoriesState.isLoading) {
                            LoadScreen()
                        }
                        DialogCardComponent(
                            onDismiss = { addProductViewModel.onClickCloseAddProduct() },
                            onConfirm = { addProductViewModel.toAddProduct(addedProductName) }
                        ) {
                            if(addProductViewModel.getCategoriesState.isSuccess) {
                                val categories = getCategoriesState.categories
                                if(categories != null){
                                    LazyRow(
                                        userScrollEnabled = true
                                    ) {
                                        items(categories){categoryResponse ->
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
                                else{
                                    LinearProgressIndicator()
                                }
                                MyTextField(
                                    textValue = addedProductName,
                                    labelValue = "продукт",
                                    onValueChange = {
                                        addedProductName = it
                                    },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                )
                            }
                            if (addProductViewModel.addProductState.isError) {
                                ErrorMessageDialog(
                                    headerText = "Что-то пошло не так",
                                    description = addProductViewModel.addProductState.error.toString(),
                                    onDismiss = {addProductViewModel.setDefaultAddProductState()}
                                )
                            }
                            if (addProductState.isSuccess) {
                                SuccessMessageDialog(
                                    text = "Продукт добавлен!",
                                    onDismiss = {
                                        addProductViewModel.setDefaultProductItem()
                                        addProductViewModel.setDefaultAddProductState()
                                        addProductViewModel.findProducts()
                                    }
                                )
                            }
                        }
                    }
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
                if(addPurchasedProductState.isError){
                    val msg = addPurchasedProductState.error.toString()
                    ErrorMessageDialog(headerText="Что-то пошло не так", description=msg, onDismiss= {
                        addPurchasedProductViewModel.setDefaultAddPurchasedProductState()
                        addPurchasedProductViewModel.setDefaultFormDataAddPurchasedProduct()
                    })
                }
            }
            else{
                NormalTextComponent(value = "Произошла ошибка при получении покупок")
            }
        }
    }
}
