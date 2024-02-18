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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.R
import com.mypurchasedproduct.presentation.navigation.PurchasedProductAppRouter
import com.mypurchasedproduct.presentation.navigation.Screen
import com.mypurchasedproduct.presentation.ViewModel.AddProductViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddPurchasedProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.ViewModel.CategoryViewModel
import com.mypurchasedproduct.presentation.ViewModel.DateRowListViewModel
import com.mypurchasedproduct.presentation.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ViewModel.MeasurementUnitsListViewModel
import com.mypurchasedproduct.presentation.ViewModel.ProductListBottomSheetViewModel
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
    addPurchasedProductFormViewModel: AddPurchasedProductFormViewModel = viewModel(),
    addProductViewModel: AddProductViewModel = viewModel(),
    categoryVM: CategoryViewModel = viewModel(),
    purchasedProductListVM: PurchasedProductListViewModel = viewModel(),
    productListBottomSheetVM: ProductListBottomSheetViewModel = viewModel(),
    measurementUnitsListVM: MeasurementUnitsListViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()
    val authState = authViewModel.state.collectAsState()
    LaunchedEffect(authState.value.isSignIn){
        Log.d("HomeScreen.LaunchedEffect", "AUTH STATE")
        if(!authState.value.isSignIn){
            appRouter.navigateTo(Screen.AuthScreen)
        }
    }
    val dateRowState = dateRowListViewModel.state.collectAsState()
    LaunchedEffect(dateRowState.value.selectedDate){
        val mills = dateRowListViewModel.getSelectedDayTimestamp()
        Log.wtf("HomeScreen.LaunchedEffect", "selected date: ${mills}\t")
        purchasedProductListVM.getPurchasedProductCurrentUserByDate(mills)
    }

    val rememberCoroutineScope = rememberCoroutineScope()
    val homeState = homeViewModel.state.collectAsState()
    LoadScreen(isActive=homeState.value.isLoading)
    Scaffold(
        topBar = {
            Column {
                DaysRowComponent(dateRowListViewModel)
                HeadingTextComponent(value = "Потрачено сегодня: ${purchasedProductListVM.totalCosts} ₽")
            }
        },
        content = {paddingValues: PaddingValues ->
            PurchasedProductViewComponent(
                purchasedProductListVM,
                paddingValues=paddingValues,
            )
        },
        floatingActionButton = {
            PrimaryFloatingActionButton(
                painter = painterResource(id = R.drawable.ic_plus),
                onClick={
                    Log.wtf("FLOATIONG BUTTON","ON CLICK FLOATIG BUTTTON: [start]")
                    rememberCoroutineScope.launch {
                        addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(true)

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

    Log.e("HOME SCREEN", "START HOME SCREEN IS SIGN IN: ${authState.value.isSignIn}")

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val getPurchasedProductsByDateState = purchasedProductListVM.state
            val deletePurchasedProductState = purchasedProductListVM.deletePurchasedProductState
            val addPurchasedProductState = addPurchasedProductFormViewModel.addPurchasedProductState
            val editPurchasedProductState = purchasedProductListVM.editPurchasedProductState

//            TODO("EDIT PURCHASED PRODUCT")

//            if(editPurchasedProductState.isActive){
//                FormModalBottomSheet(
//                    openBottomSheet = editPurchasedProductState.isActive,
//                    setStateButtomSheet = {
//                        purchasedProductListVM.setActiveEditPurchasedProduct(it)
//                    }
//                )
//                {
//                    if (editPurchasedProductState.purchasedProduct != null) {
//                        EditPurchasedProductFormComponent(
//                            products = addProductViewModel.getProductsState.products,
//                            measurementUnits = measurementUnits,
//                            onClickAddProduct = {
//                                addProductViewModel.onClickAddProduct()
//                                addProductViewModel.findCategories()
//                            },
//                            onConfirm = {
//                                purchasedProductListVM.toEditPurchasedProduct(it)
//                                        },
//                            onDismiss = {
//                                purchasedProductListVM.setActiveEditPurchasedProduct(false)
//                                purchasedProductListVM.setDefaultEditPurchasedProductState()
//                            },
//                            purchasedProduct = editPurchasedProductState.purchasedProduct
//                        )
//                    }
//                }
//
//            }

            if(editPurchasedProductState.isSuccess){
                SuccessMessageDialog(
                    text ="купленный продукт изменен",
                    onDismiss = {
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
                        addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(it)
                    }
                )
                {
                    AddPurchasedProductFormComponent(
                        addPurchasedProductVM = addPurchasedProductFormViewModel,
                        productListBottomSheetVM = productListBottomSheetVM,
                        measurementUnitsListVM = measurementUnitsListVM,
                        onClickAddProduct = {
                            addProductViewModel.onClickAddProduct()
                            addProductViewModel.findCategories()
                                            },
                        onConfirm = {
                            scope.launch{
                                purchasedProductListVM.toAddPurchasedProduct(it, dateRowListViewModel.getSelectedDayTimestamp())
                            }

                                    },
                        onDismiss = {addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(false)}
                    )
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

        }

}
