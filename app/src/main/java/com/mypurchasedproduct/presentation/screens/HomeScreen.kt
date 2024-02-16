package com.mypurchasedproduct.presentation.screens


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.ViewModel.AddProductViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddPurchasedProductViewModel
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.ViewModel.CategoryViewModel
import com.mypurchasedproduct.presentation.ViewModel.DateRowListViewModel
import com.mypurchasedproduct.presentation.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ViewModel.PurchasedProductListViewModel
import com.mypurchasedproduct.presentation.ui.components.AddCategoryForm
import com.mypurchasedproduct.presentation.ui.components.AddPurchasedProductFormComponent
import com.mypurchasedproduct.presentation.ui.components.AlertDialogComponent
import com.mypurchasedproduct.presentation.ui.components.DaysRowComponent
import com.mypurchasedproduct.presentation.ui.components.DialogCardComponent
import com.mypurchasedproduct.presentation.ui.components.EditPurchasedProductFormComponent
import com.mypurchasedproduct.presentation.ui.components.ErrorMessageDialog
import com.mypurchasedproduct.presentation.ui.components.FormModalBottomSheet
import com.mypurchasedproduct.presentation.ui.components.HeadingTextComponent
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.MyTextField
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryButtonComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryFloatingActionButton
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductViewComponent
import com.mypurchasedproduct.presentation.ui.components.SelectCategoryButton
import com.mypurchasedproduct.presentation.ui.components.SuccessMessageDialog
import com.mypurchasedproduct.presentation.ui.theme.DeepBlackColor
import kotlinx.coroutines.launch
import org.joda.time.Instant


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    appRouter: PurchasedProductAppRouter = PurchasedProductAppRouter,
    homeViewModel: HomeViewModel = viewModel(),
    dateRowListViewModel: DateRowListViewModel = viewModel(),
    addPurchasedProductViewModel: AddPurchasedProductViewModel = viewModel(),
    addProductViewModel: AddProductViewModel = viewModel(),
    categoryVM: CategoryViewModel = viewModel(),
    purchasedProductListVM: PurchasedProductListViewModel = viewModel(),
) {

    val dateRowState = dateRowListViewModel.state.collectAsState()
    LaunchedEffect(dateRowState.value.selectedDate){
        val selectedDay = dateRowState.value.selectedDate
        val mills: Long = Instant.parse("${selectedDay.year}-${selectedDay.month}-${selectedDay.dayOfMonth}").millis
        Log.wtf("HomeScreen", "selected date: ${mills}")
    }

    val authState = authViewModel.state.collectAsState()
    if(!authState.value.isSignIn){
        appRouter.navigateTo(Screen.AuthScreen)
    }
    val rememberCoroutineScope = rememberCoroutineScope()
    val homeState = homeViewModel.state

    Log.e("HOME SCREEN", "START HOME SCREEN IS SIGN IN: ${homeState.isSignIn}")

    if(homeState.isSignIn == null){
        homeViewModel.checkAccessToken()
    }
    else if(!homeState.isSignIn){
//        appRouter.navigateTo(Screen.SignUpScreen)
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
            val getPurchasedProductsByDateState = purchasedProductListVM.getPurchasedProductsByDateState
            val deletePurchasedProductState = purchasedProductListVM.deletePurchasedProductState
            val addPurchasedProductState = addPurchasedProductViewModel.addPurchasedProductState
            val editPurchasedProductState = purchasedProductListVM.editPurchasedProductState
            val purchasedProducts: List<PurchasedProductResponse> = getPurchasedProductsByDateState.purchasedProducts
            val measurementUnits = addPurchasedProductViewModel.getMeasurementUnits()

            if(editPurchasedProductState.isActive){
                FormModalBottomSheet(
                    openBottomSheet = editPurchasedProductState.isActive,
                    setStateButtomSheet = {
                        purchasedProductListVM.setActiveEditPurchasedProduct(it)
                    }
                )
                {
                    if (editPurchasedProductState.purchasedProduct != null) {
                        EditPurchasedProductFormComponent(
                            products = addProductViewModel.getProductsState.products,
                            measurementUnits = measurementUnits,
                            onClickAddProduct = {
                                addProductViewModel.onClickAddProduct()
                                addProductViewModel.findCategories()
                            },
                            onConfirm = {
                                purchasedProductListVM.toEditPurchasedProduct(it)
                                        },
                            onDismiss = {
                                purchasedProductListVM.setActiveEditPurchasedProduct(false)
                                purchasedProductListVM.setDefaultEditPurchasedProductState()
                            },
                            purchasedProduct = editPurchasedProductState.purchasedProduct
                        )
                    }
                }

            }

            if(editPurchasedProductState.isSuccess){
                SuccessMessageDialog(
                    text ="купленный продукт изменен",
                    onDismiss = {
                        purchasedProductListVM.getPurchasedProductCurrentUserByDate()
                        purchasedProductListVM.setDefaultEditPurchasedProductState()
                        purchasedProductListVM.setActiveEditPurchasedProduct(false)
                    })
            }
            if(editPurchasedProductState.isError){
                ErrorMessageDialog(
                    headerText ="Что-то пошло не так" ,
                    description = editPurchasedProductState.error.toString(),
                    onDismiss = {
                        purchasedProductListVM.setActiveEditPurchasedProduct(false)
                        purchasedProductListVM.setDefaultEditPurchasedProductState()

                    }
                )
            }
            // TODO: ADDED ADD PURCHASED PRODUCT FORM COMPONENT
            if(addPurchasedProductState.isActive) {

                FormModalBottomSheet(
                    openBottomSheet = addPurchasedProductState.isActive,
                    setStateButtomSheet = {
                        addPurchasedProductViewModel.setActiveAddPurchasedProductForm(it)
                    }
                )
                {
                    AddPurchasedProductFormComponent(
                        products =addProductViewModel.getProductsState.products,
                        measurementUnits = measurementUnits,
                        onClickAddProduct = {
                            addProductViewModel.onClickAddProduct()
                            addProductViewModel.findCategories()
                                            },
                        onConfirm = {
                            addPurchasedProductViewModel.toAddPurchasedProduct(it)
                                    },
                        onDismiss = {addPurchasedProductViewModel.setActiveAddPurchasedProductForm(false)}
                    )
                }

                if (addProductViewModel.getProductsState.isUpdating || addPurchasedProductViewModel.findMeasurementUnits.isUpdating) {
                    LoadScreen()
                }
                if (addProductViewModel.getProductsState.isSuccess && addPurchasedProductViewModel.findMeasurementUnits.isSuccess) {

                }
            }

            if(categoryVM.addCategoryState.isActive){
                val addCategoryState = categoryVM.addCategoryState
                AddCategoryForm(
                    isLoading=addCategoryState.isLoading,
                    onConfirm = {categoryVM.addCategory(it)},
                    onDismiss = {categoryVM.setActiveAddCategory(it)})
                if(addCategoryState.isSuccess){
                    SuccessMessageDialog(
                        text = "категория добавлена!",
                        onDismiss = {
                            addProductViewModel.findCategories()
                            categoryVM.setActiveAddCategory(false)
                            categoryVM.setDefaultState()
                        })
                }
                if(addCategoryState.isError){
                    ErrorMessageDialog(
                        headerText = "Что-то пошло не так",
                        description = addCategoryState.error.toString(),
                        onDismiss = {
                            categoryVM.setActiveAddCategory(false)
                            categoryVM.setDefaultState()
                        })
                }
            }
            if (addProductViewModel.addProductFormState.isActive) {
                val getCategoriesState = addProductViewModel.getCategoriesState
//                        val productItem = addProductViewModel.productItem
                var addedProductName by remember {
                    mutableStateOf("")
                }
                val addProductState = addProductViewModel.addProductState
                DialogCardComponent(
                    onDismiss = { addProductViewModel.onClickCloseAddProduct() },
                    onConfirm = { addProductViewModel.toAddProduct(addedProductName) }
                ) {
                    if(addProductViewModel.getCategoriesState.isSuccess) {
                        val categories = getCategoriesState.categories
                        if(categories != null){
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(.85f),
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
                                IconButton(onClick = { categoryVM.setActiveAddCategory(true) }) {
                                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
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
            if(getPurchasedProductsByDateState.isActive){
//                LoadScreen()
                purchasedProductListVM.getPurchasedProductCurrentUserByDate()

            }
            else if(getPurchasedProductsByDateState.isSuccessResponse){
//                TODO("VIEW PURCHASED PRODUCTS BY DATE")
                Scaffold(
                    topBar = {
                        Column {
                            DaysRowComponent(dateRowListViewModel)
                            HeadingTextComponent(value = "Потрачено сегодня: ${purchasedProductListVM.totalCosts} ₽")
                        }
                             },
                    content = {paddingValues: PaddingValues ->
                        if(getPurchasedProductsByDateState.isLoading){
                            Column(
                                modifier= Modifier
                                    .fillMaxSize()
                                    .background(color = Color.White),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.height(45.dp),
                                    color = DeepBlackColor
                                )

                            }
                        }
                        else{
                            PurchasedProductViewComponent(purchasedProducts,
                                paddingValues=paddingValues,
                                onSwipeDeletePurchasedProduct={purchasedProductListVM.onSwipeDelete(it)},
                                onSwipeEditPurchasedProduct={
                                    purchasedProductListVM.onSwipeEditPurchasedProduct(it)
                                    addProductViewModel.findProducts()
                                    addPurchasedProductViewModel.findMeasurementUnits()
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        PrimaryFloatingActionButton(
                            painter = painterResource(id = R.drawable.ic_plus),
                            onClick={
                                Log.wtf("FLOATIONG BUTTON","ON CLICK FLOATIG BUTTTON: [start]")
                                rememberCoroutineScope.launch {
                                    addPurchasedProductViewModel.setActiveAddPurchasedProductForm(true)
                                    addProductViewModel.findProducts()
                                    addPurchasedProductViewModel.findMeasurementUnits()
                                }
                                Log.wtf("FLOATIONG BUTTON","ON CLICK FLOATIG BUTTTON: [END]")
                            },

                            )
                    },
                    bottomBar = {
                        PrimaryButtonComponent(
                            value = "Выйти", onClickButton = {
                                authViewModel.signOut()
                                appRouter.navigateTo(Screen.AuthScreen)
                            }
                        )
                    }
                )

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
                            onDismiss = {
                                purchasedProductListVM.setDefaultDeletePurchasedProductState()
                                purchasedProductListVM.setUpdatePurchasedProductByDate(true)
                                homeViewModel.setLoadingState(false)
                            }
                        )
                    }
                    if(deletePurchasedProductState.isError){
                        ErrorMessageDialog(
                            headerText = "Что-то пошло не так",
                            description =deletePurchasedProductState.error.toString(),
                            onDismiss = {
                                purchasedProductListVM.setDefaultDeletePurchasedProductState()
                                homeViewModel.setLoadingState(false)
                            }
                        )
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
