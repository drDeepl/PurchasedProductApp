package com.mypurchasedproduct.presentation.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mypurchasedproduct.R
import com.mypurchasedproduct.presentation.ViewModel.AddCategoryFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddPurchasedProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.ViewModel.CategoryListViewModel
import com.mypurchasedproduct.presentation.ViewModel.DateRowListViewModel
import com.mypurchasedproduct.presentation.ViewModel.EditPurchasedProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ViewModel.MeasurementUnitsListViewModel
import com.mypurchasedproduct.presentation.ViewModel.ProductListViewModel
import com.mypurchasedproduct.presentation.ViewModel.PurchasedProductListViewModel
import com.mypurchasedproduct.presentation.navigation.ModalBottomSheetNavigation
import com.mypurchasedproduct.presentation.navigation.ScreenNavigation
import com.mypurchasedproduct.presentation.ui.components.AddCategoryForm
import com.mypurchasedproduct.presentation.ui.components.AddProductFormComponent
import com.mypurchasedproduct.presentation.ui.components.AddPurchasedProductFormComponent
import com.mypurchasedproduct.presentation.ui.components.AlertDialogComponent
import com.mypurchasedproduct.presentation.ui.components.DaysRowComponent
import com.mypurchasedproduct.presentation.ui.components.EditPurchasedProductFormComponent
import com.mypurchasedproduct.presentation.ui.components.ErrorMessageDialog
import com.mypurchasedproduct.presentation.ui.components.FormModalBottomSheet
import com.mypurchasedproduct.presentation.ui.components.HeadingTextComponent
import com.mypurchasedproduct.presentation.ui.components.LoadScreen
import com.mypurchasedproduct.presentation.ui.components.NormalTextComponent
import com.mypurchasedproduct.presentation.ui.components.PrimaryFloatingActionButton
import com.mypurchasedproduct.presentation.ui.components.PrimaryGradientButtonComponent
import com.mypurchasedproduct.presentation.ui.components.ProductListComponent
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductViewComponent
import com.mypurchasedproduct.presentation.ui.components.SuccessMessageDialog
import kotlinx.coroutines.launch


@Composable

fun HomeScreen(
    authViewModel: AuthViewModel,
    screenNavController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    dateRowListViewModel: DateRowListViewModel = hiltViewModel(),
    addPurchasedProductFormViewModel: AddPurchasedProductFormViewModel = hiltViewModel(),
    addProductFormViewModel: AddProductFormViewModel = hiltViewModel(),
    categoryVM: AddCategoryFormViewModel = hiltViewModel(),
    categoryListVM: CategoryListViewModel = hiltViewModel(),
    purchasedProductListVM: PurchasedProductListViewModel = hiltViewModel(),
    productListVM: ProductListViewModel = hiltViewModel(),
    measurementUnitsListVM: MeasurementUnitsListViewModel = hiltViewModel(),
    editPurchasedProductFormVM: EditPurchasedProductFormViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val homeState = homeViewModel.state.collectAsState()
    val authState = authViewModel.state.collectAsState()
    LaunchedEffect(authState.value.isSignIn){
        Log.d("HomeScreen.LaunchedEffect", "AUTH STATE")
        if(!authState.value.isSignIn){
            screenNavController.navigate(ScreenNavigation.AuthScreenRoute)
        }
    }
    val dateRowState = dateRowListViewModel.state.collectAsState()
    LaunchedEffect(dateRowState.value.selectedDate){
        val mills = dateRowListViewModel.getSelectedDayTimestamp()
        Log.wtf("HomeScreen.LaunchedEffect", "selected date: ${mills}\t")
        purchasedProductListVM.getPurchasedProductCurrentUserByDate(mills)
    }

    val rememberCoroutineScope = rememberCoroutineScope()

    val totalCosts = purchasedProductListVM.totalCosts.collectAsState()
    LoadScreen(isActive=homeState.value.isLoading)
    val msgState = homeViewModel.msgState.collectAsState()
    if(msgState.value.isSuccess){
        SuccessMessageDialog(
            text = msgState.value.header,
            onDismiss = {
                scope.launch{
                    msgState.value.onConfirm()
                    homeViewModel.setDefaultMsgState()
                }
            }
        )
    }
    if(msgState.value.isError){
        ErrorMessageDialog(
            headerText = msgState.value.header,
            description = msgState.value.description,
            onDismiss = {
                homeViewModel.setDefaultMsgState()
                addPurchasedProductFormViewModel.setDefaultState()
            })
    }
    val deletePurchasedProductState = purchasedProductListVM.deletePurchasedProductState.collectAsState()
    if(deletePurchasedProductState.value.isActive){
        AlertDialogComponent(
            headerText="Удалить купленный продукт?",
            onDismiss = {purchasedProductListVM.onDismissDeletePurchasedProduct()},
            onConfirm = {purchasedProductListVM.deletePurchasedProduct()},
        )
        {
            NormalTextComponent(value = "Будет удалено: ${deletePurchasedProductState.value?.purchasedProduct?.product?.name}")
        }
        if(deletePurchasedProductState.value.isSuccess){
            SuccessMessageDialog(
                text = "Купленный продукт удален!",
                onDismiss = {
                    purchasedProductListVM.setDefaultDeletePurchasedProductState()
                    homeViewModel.setLoadingState(false)
                }
            )
        }
        if(deletePurchasedProductState.value.isError){
            ErrorMessageDialog(
                headerText = "Что-то пошло не так",
                description = deletePurchasedProductState.value.error.toString(),
                onDismiss = {
                    purchasedProductListVM.setDefaultDeletePurchasedProductState()
                    homeViewModel.setLoadingState(false)
                }
            )
        }
    }
    val bottomSheetActive = remember {mutableStateOf(false)}
    Scaffold(
        topBar = {
            Column {
                DaysRowComponent(dateRowListViewModel)
                HeadingTextComponent(value = "Потрачено сегодня: ${totalCosts.value} ₽")
            }
        },
        content = {paddingValues: PaddingValues ->
            val addPurchasedProductState = addPurchasedProductFormViewModel.state.collectAsState()
            val startDestination =
                remember { mutableStateOf(ModalBottomSheetNavigation.AddPurchasedProductRoute) }
            PurchasedProductViewComponent(
                purchasedProductListVM,
                paddingValues=paddingValues,
                onSwipeToEdit = {



//                    editPurchasedProductFormVM.setActive(true)
                    scope.launch {
                        startDestination.value = ModalBottomSheetNavigation.EditPurhcasedProductRoute
                        editPurchasedProductFormVM.setPurchasedProduct(it)
                        bottomSheetActive.value = !bottomSheetActive.value }

                }
            )
//            addPurchasedProductState.value.isActive,
            FormModalBottomSheet(
                openBottomSheet = bottomSheetActive.value,
                setStateBottomSheet = {
                    bottomSheetActive.value = it
//                    scope.launch {
//                        addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(it)
//
//                    }

                }
            )
            {
                // TODO: NAV HOST
                NavHost(
                    navController = navController ,
                    startDestination = startDestination.value,
                    route = ModalBottomSheetNavigation.NavHostRoute
                ){
                    composable(route = ModalBottomSheetNavigation.AddPurchasedProductRoute){
                        productListVM.state
                        AddPurchasedProductFormComponent(
                            addPurchasedProductVM = addPurchasedProductFormViewModel,
                            measurementUnitsListVM = measurementUnitsListVM,
                            onClickAddProduct = {
                                navController.navigate(route=ModalBottomSheetNavigation.AddProductRoute)
                                scope.launch{
//                                    productListVM.findProducts()
//                                    categoryListVM.findCategories()
                                }

                            },
                            onConfirm = {
                                scope.launch{
                                    purchasedProductListVM.toAddPurchasedProduct(
                                        it,
                                        dateRowListViewModel.getSelectedDayTimestamp(),
                                        onSuccess = {
                                            homeViewModel.setSuccessMsgState(
                                                header=it,
                                                onConfirm = { addPurchasedProductFormViewModel.setDefaultState() }
                                            )})
                                }

                            },
                            onDismiss = {
                                scope.launch{
                                    bottomSheetActive.value = false
                                    addPurchasedProductFormViewModel.setDefaultState()
                                }

                                        },
                            onSelectProduct = {
                                navController.navigate(route=ModalBottomSheetNavigation.ProductListRoute)
                            }
                        )
                    }
                    composable(route=ModalBottomSheetNavigation.ProductListRoute){
                        ProductListComponent(
                            viewModel = productListVM,
                            onClickAddProduct = {
                                navController.navigate(route=ModalBottomSheetNavigation.AddProductRoute)
                                categoryListVM.findCategories()
                            },
                            onClickProductItem = {
                                addPurchasedProductFormViewModel.setProduct(it)
                                navController.navigate(ModalBottomSheetNavigation.AddPurchasedProductRoute)

                            }
                        )

                    }
                    composable(route = ModalBottomSheetNavigation.AddProductRoute){
                        AddProductFormComponent(
                            addProductFormViewModel = addProductFormViewModel,
                            categoryListVM = categoryListVM,
                            onConfirm = {
                                        productListVM.toAddProduct(it)
                            },
                            onDismiss = {
                                scope.launch {
                                    navController.navigate(startDestination.value)
                                    addProductFormViewModel.setDefaultState()
                                }

                            },
                            categories=categoryListVM.categories.collectAsState()
                        )
                    }
                    composable(route=ModalBottomSheetNavigation.EditPurhcasedProductRoute){
                        EditPurchasedProductFormComponent(
                            editPurchasedProductVM = editPurchasedProductFormVM,
                            measurementUnitsListVM = measurementUnitsListVM,
                            onConfirm = {editPurchasedProductModel ->
                                purchasedProductListVM.toEditPurchasedProduct(
                                    editPurchasedProductModel,
                                    onError = {
                                        homeViewModel.setErrorMsgState("что-то пошло не так", it, onConfirm = {homeViewModel.setDefaultMsgState()}, onDismiss={}) }
                                )
                            },
                            onClickAddProduct = {
                                navController.navigate(route=ModalBottomSheetNavigation.AddProductRoute)

                            },
                            onDismiss = {
                                scope.launch {
                                    bottomSheetActive.value = false
                                    startDestination.value = ModalBottomSheetNavigation.AddPurchasedProductRoute
                                    editPurchasedProductFormVM.setDefaultState()
                                    editPurchasedProductFormVM.clearErrors()
                                }

                            }

                        )
                    }
                }

//                if (addProductFormState.value.isError) {
//                    ErrorMessageDialog(
//                        headerText = "Что-то пошло не так",
//                        description = "todo",
//                        onDismiss = {
//                            addProductFormViewModel.setDefaultState()
//                        }
//                    )
//                }
//                if (addProductFormState.value.isSuccess) {
//                    SuccessMessageDialog(
//                        text = "Продукт добавлен!",
//                        onDismiss = {
//                            addProductFormViewModel.setDefaultState()
//                        }
//                    )
//                }

            }

        },
        floatingActionButton = {
            PrimaryFloatingActionButton(
                painter = painterResource(id = R.drawable.ic_plus),
                onClick= {
                    bottomSheetActive.value = !bottomSheetActive.value
//                    rememberCoroutineScope.launch {
//                        addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(true)
//                    }
                         },
                )
        },
        bottomBar = {
            PrimaryGradientButtonComponent(
                value = "Выйти", onClickButton = {
                    screenNavController.navigate(ScreenNavigation.AuthScreenRoute){
                        popUpTo(ScreenNavigation.NavHostRoute){
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    authViewModel.signOut()

                }
            )
        }
    )
    Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val deletePurchasedProductState = purchasedProductListVM.deletePurchasedProductState.collectAsState()
            val editPurchasedProductState = editPurchasedProductFormVM.state.collectAsState()

//            TODO("EDIT PURCHASED PRODUCT")
//        FormModalBottomSheet(
//            openBottomSheet = editPurchasedProductState.value.isActive,
//            setStateBottomSheet = {
//                editPurchasedProductFormVM.setActive(it)
//            },
//            onDismissRequest = {
//                editPurchasedProductFormVM.setDefaultState()
//                editPurchasedProductFormVM.clearErrors()
//            }
//        ){
//
//        }

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

            if(editPurchasedProductState.value.isSuccess){
                SuccessMessageDialog(
                    text ="купленный продукт изменен",
                    onDismiss = {
                        purchasedProductListVM.setDefaultEditPurchasedProductState()
//                        purchasedProductListVM.setActiveEditPurchasedProduct(false)
                    })
            }
//            if(editPurchasedProductState.value.isError){
//                ErrorMessageDialog(
//                    headerText ="Что-то пошло не так" ,
//                    description = editPurchasedProductState.value.error.toString(),
//                    onDismiss = {
////                        purchasedProductListVM.setActiveEditPurchasedProduct(false)
//                        purchasedProductListVM.setDefaultEditPurchasedProductState()
//
//                    }
//                )
//            }


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
        }

}
