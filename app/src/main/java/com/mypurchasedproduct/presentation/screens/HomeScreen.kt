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
import com.mypurchasedproduct.presentation.ViewModel.ProductListBottomSheetViewModel
import com.mypurchasedproduct.presentation.ViewModel.PurchasedProductListViewModel
import com.mypurchasedproduct.presentation.navigation.BottomSheetNavigation
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
    productListBottomSheetVM: ProductListBottomSheetViewModel = hiltViewModel(),
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
//            appRouter.navigateTo(Screen.AuthScreen)
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
    Scaffold(
        topBar = {
            Column {
                DaysRowComponent(dateRowListViewModel)
                HeadingTextComponent(value = "Потрачено сегодня: ${totalCosts.value} ₽")
            }
        },
        content = {paddingValues: PaddingValues ->
            val addPurchasedProductState = addPurchasedProductFormViewModel.state.collectAsState()
            val addProductFormState = addProductFormViewModel.formState.collectAsState()
            PurchasedProductViewComponent(
                purchasedProductListVM,
                paddingValues=paddingValues,
                onSwipeToEdit = {
                    editPurchasedProductFormVM.setActive(true)
                    editPurchasedProductFormVM.setPurchasedProduct(it)
                }
            )
            FormModalBottomSheet(
                openBottomSheet = addPurchasedProductState.value.isActive,
                setStateBottomSheet = {
                    scope.launch {
                        addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(it)
                    }

                }
            )
            {
                // TODO: NAV HOST
                NavHost(navController = navController , startDestination = BottomSheetNavigation.AddPurchasedProductRoute){
                    composable(route = BottomSheetNavigation.AddPurchasedProductRoute){
                        AddPurchasedProductFormComponent(
                            addPurchasedProductVM = addPurchasedProductFormViewModel,
                            productListBottomSheetVM = productListBottomSheetVM,
                            measurementUnitsListVM = measurementUnitsListVM,
                            onClickAddProduct = {
//                                addProductFormViewModel.setActiveForm(true)
                                navController.navigate(route=BottomSheetNavigation.AddProductRoute)
                                categoryListVM.findCategories()
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
                            onDismiss = {addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(false)}
                        )
                    }
                    composable(route = BottomSheetNavigation.AddProductRoute){
                        AddProductFormComponent(
                            addProductFormViewModel = addProductFormViewModel,
                            categoryListVM = categoryListVM,
                            onDismiss = {
                                addProductFormViewModel.setActiveForm(false)
                                addProductFormViewModel.setDefaultState()
                            }
                        )
                    }
                    composable(route=BottomSheetNavigation.ProductListRoute){

                    }
                }

                if (addProductFormState.value.isError) {
                    ErrorMessageDialog(
                        headerText = "Что-то пошло не так",
                        description = "todo",
                        onDismiss = {
                            addProductFormViewModel.setDefaultState()
                        }
                    )
                }
                if (addProductFormState.value.isSuccess) {
                    SuccessMessageDialog(
                        text = "Продукт добавлен!",
                        onDismiss = {
                            addProductFormViewModel.setDefaultState()
                        }
                    )
                }

            }

        },
        floatingActionButton = {
            PrimaryFloatingActionButton(
                painter = painterResource(id = R.drawable.ic_plus),
                onClick= {
                    rememberCoroutineScope.launch {
                        addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(true)
                    }
                         },
                )
        },
        bottomBar = {
            PrimaryGradientButtonComponent(
                value = "Выйти", onClickButton = {
                    authViewModel.signOut()
                    screenNavController.navigate(ScreenNavigation.AuthScreenRoute)
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
        FormModalBottomSheet(
            openBottomSheet = editPurchasedProductState.value.isActive,
            setStateBottomSheet = {
                editPurchasedProductFormVM.setActive(it)
            },
            onDismissRequest = {
                editPurchasedProductFormVM.setDefaultState()
                editPurchasedProductFormVM.clearErrors()
            }
        ){
            EditPurchasedProductFormComponent(
                editPurchasedProductVM = editPurchasedProductFormVM,
                productListBottomSheetVM = productListBottomSheetVM,
                measurementUnitsListVM = measurementUnitsListVM,
                onConfirm = {},
                onClickAddProduct = {},
                onDismiss = {
                    editPurchasedProductFormVM.setDefaultState()
                    editPurchasedProductFormVM.clearErrors()
                }
            )
        }

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
