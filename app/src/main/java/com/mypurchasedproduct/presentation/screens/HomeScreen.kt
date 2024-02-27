package com.mypurchasedproduct.presentation.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mypurchasedproduct.R
import com.mypurchasedproduct.presentation.ViewModel.AddCategoryFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddMeasurementUnitViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddPurchasedProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AuthViewModel
import com.mypurchasedproduct.presentation.ViewModel.CategoryListViewModel
import com.mypurchasedproduct.presentation.ViewModel.DateRowListViewModel
import com.mypurchasedproduct.presentation.ViewModel.DialogMessageViewModel
import com.mypurchasedproduct.presentation.ViewModel.EditPurchasedProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.HomeViewModel
import com.mypurchasedproduct.presentation.ViewModel.MeasurementUnitsListViewModel
import com.mypurchasedproduct.presentation.ViewModel.ProductListViewModel
import com.mypurchasedproduct.presentation.ViewModel.PurchasedProductListViewModel
import com.mypurchasedproduct.presentation.navigation.ModalBottomSheetNavigation
import com.mypurchasedproduct.presentation.navigation.ScreenNavigation
import com.mypurchasedproduct.presentation.ui.components.AddCategoryFormComponent
import com.mypurchasedproduct.presentation.ui.components.AddMeasurementUnitFormComponent
import com.mypurchasedproduct.presentation.ui.components.AddProductFormComponent
import com.mypurchasedproduct.presentation.ui.components.AddPurchasedProductFormComponent
import com.mypurchasedproduct.presentation.ui.components.AlertDialogFrozen
import com.mypurchasedproduct.presentation.ui.components.DialogCardComponent
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
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductItemLoading
import com.mypurchasedproduct.presentation.ui.components.PurchasedProductViewComponent
import com.mypurchasedproduct.presentation.ui.components.SuccessMessageDialog
import com.mypurchasedproduct.presentation.ui.theme.lowPadding
import kotlinx.coroutines.launch


@Composable

fun HomeScreen(
    authViewModel: AuthViewModel,
    screenNavController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    dateRowListViewModel: DateRowListViewModel = hiltViewModel(),
    addPurchasedProductFormViewModel: AddPurchasedProductFormViewModel = hiltViewModel(),
    productListVM: ProductListViewModel = hiltViewModel(),
    measurementUnitsListVM: MeasurementUnitsListViewModel = hiltViewModel(),
    editPurchasedProductFormVM: EditPurchasedProductFormViewModel = hiltViewModel(),

) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val authState = authViewModel.state.collectAsState()
    LaunchedEffect(authState.value.isSignIn){
        Log.d("HomeScreen.LaunchedEffect", "AUTH STATE")
        if(!authState.value.isSignIn){
            screenNavController.navigate(ScreenNavigation.AuthScreenRoute)
        }
    }
    val purchasedProductListVM = hiltViewModel<PurchasedProductListViewModel>()
    val homeState = homeViewModel.state.collectAsState()
    val dateRowState = dateRowListViewModel.state.collectAsState()
    LaunchedEffect(dateRowState.value.selectedDate){
        val mills = dateRowListViewModel.getSelectedDayTimestamp()
        Log.wtf("HomeScreen.LaunchedEffect", "selected date: ${mills}\t")
        purchasedProductListVM.getPurchasedProductCurrentUserByDate(mills)


    }
    val dialogMessageVM = hiltViewModel<DialogMessageViewModel>()
    val totalCosts = purchasedProductListVM.totalCosts.collectAsState()
    LoadScreen(isActive=homeState.value.isLoading)
    val successDialogState = dialogMessageVM.successDialogState.collectAsState()
    if(successDialogState.value.isActive){
        SuccessMessageDialog(
            text = successDialogState.value.header,
            onDismiss = {
                scope.launch{
                    successDialogState.value.onConfirm()
                    dialogMessageVM.setDefaultSuccessState()
                }
            }
        )
    }
    val errorDialogState = dialogMessageVM.errorDialogState.collectAsState()
    if(errorDialogState.value.isActive){
        ErrorMessageDialog(
            header = errorDialogState.value.header,
            errors = errorDialogState.value.errors,
            onDismiss = {
                scope.launch {
                    dialogMessageVM.errorDialogState.value.onConfirm()
                    dialogMessageVM.setDefaultErrorState()
                }
            })
    }
    val deletePurchasedProductState = purchasedProductListVM.deletePurchasedProductState.collectAsState()
    if(deletePurchasedProductState.value.isActive){
        DialogCardComponent(

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
            val scaffoldScope = rememberCoroutineScope()
            val startDestination =
                remember { mutableStateOf(ModalBottomSheetNavigation.AddPurchasedProductRoute) }
            val categoryListVM: CategoryListViewModel = hiltViewModel()
            PurchasedProductViewComponent(
                purchasedProductListVM,
                paddingValues=paddingValues,
                onSwipeToEdit = {
//                    editPurchasedProductFormVM.setActive(true)
                    scaffoldScope.launch {
                        startDestination.value = ModalBottomSheetNavigation.EditPurhcasedProductRoute
                        editPurchasedProductFormVM.setPurchasedProduct(it)
                        bottomSheetActive.value = true
                    }.invokeOnCompletion { purchasedProductListVM.setLoading(true) }

                }
            )
//            addPurchasedProductState.value.isActive,
            FormModalBottomSheet(
                openBottomSheet = bottomSheetActive.value,
                setStateBottomSheet = {
                    bottomSheetActive.value = it
                    purchasedProductListVM.setLoading(it)
//                    scope.launch {
//                        addPurchasedProductFormViewModel.setActiveAddPurchasedProductForm(it)
//
//                    }
                },
            )
            {
                // TODO: NAV HOST
                NavHost(
                    navController = navController ,
                    startDestination = startDestination.value,
                    route = ModalBottomSheetNavigation.NavHostRoute
                ){
                    composable(route = ModalBottomSheetNavigation.AddPurchasedProductRoute){
                        AddPurchasedProductFormComponent(
                            addPurchasedProductVM = addPurchasedProductFormViewModel,
                            measurementUnitsListVM = measurementUnitsListVM,
                            onClickAddMeasurementUnit = {
                                navController.navigate(route=ModalBottomSheetNavigation.AddMeasurementUnitRoute)
                            },
                            onConfirm = {
                                scope.launch{
                                    purchasedProductListVM.toAddPurchasedProduct(
                                        it,
                                        dateRowListViewModel.getSelectedDayTimestamp(),
                                        onSuccess = {
                                            dialogMessageVM.setSuccessDialogState(
                                                header=it,
                                                onConfirm = { addPurchasedProductFormViewModel.setDefaultState() }
                                            )},
                                        onError = { header, errors ->
                                            dialogMessageVM.setErrorDialogState(
                                                header=header,
                                                errors = errors,
                                                onConfirm = {})
                                        }
                                    )
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
                        val products = productListVM.products.collectAsState()
                        AlertDialogFrozen(){
                            Scaffold(
                                content = {
                                    ProductListComponent(
                                        products= products,
                                        onClickProductItem = {
                                            addPurchasedProductFormViewModel.setProduct(it)
                                            navController.navigate(ModalBottomSheetNavigation.AddPurchasedProductRoute)
                                        },
                                        modifier = Modifier.padding(it)
                                    )
                                },
                                bottomBar = {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = "Нет нужного продукта?")
                                        }
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            TextButton(
                                                onClick = {
                                                    navController.navigate(route = ModalBottomSheetNavigation.AddPurchasedProductRoute)
                                                }
                                            ) {
                                                Icon(
                                                    painter = rememberVectorPainter(image = Icons.Filled.ArrowCircleLeft),
                                                    contentDescription = ""
                                                )
                                                Spacer(modifier = Modifier.padding(lowPadding))
                                                Text("назад", fontSize = 16.sp)

                                            }
                                            Divider(
                                                modifier = Modifier
                                                    .rotate(90f)
                                                    .width(25.dp), thickness = 2.dp
                                            )
                                            TextButton(
                                                onClick = {
                                                    navController.navigate(route = ModalBottomSheetNavigation.AddProductRoute)
                                                }
                                            ) {
                                                Text("добавить", fontSize = 16.sp)
                                            }

                                        }
                                        Divider(
                                            modifier = Modifier.padding(5.dp, 10.dp),
                                            thickness = 2.dp
                                        )
                                    }

                                }
                            )
                        }
                    }
                    composable(route = ModalBottomSheetNavigation.AddProductRoute){
                        val addProductFormViewModel: AddProductFormViewModel = hiltViewModel()
                        AddProductFormComponent(
                            addProductFormViewModel = addProductFormViewModel,
                            categoryListVM = categoryListVM,
                            onClickAddCategory = {navController.navigate(route=ModalBottomSheetNavigation.AddCategoryRoute)},
                            onConfirm = {
                                        productListVM.toAddProduct(
                                            it,
                                            onSuccess = {
                                                dialogMessageVM.setSuccessDialogState(
                                                    header=it,
                                                    onConfirm = {
                                                        addProductFormViewModel.setDefaultState()
                                                        navController.navigate(route=ModalBottomSheetNavigation.ProductListRoute)
                                                    }
                                                )
                                                        },
                                            onError = { header, errors ->
                                                dialogMessageVM.setErrorDialogState(
                                                    header=header,
                                                    errors = errors,
                                                    onConfirm = {}
                                                )
                                            })
                                purchasedProductListVM.setLoading(false)
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
                                    onError = {header, errors ->
                                    dialogMessageVM.setErrorDialogState(
                                        header=header,
                                        errors =errors,
                                        onConfirm = {})
                                    },
                                    onSuccess = {header ->
//                                        purchasedProductListVM.getPurchasedProductCurrentUserByDate(dateRowListViewModel.getSelectedDayTimestamp())
                                        dialogMessageVM.setSuccessDialogState(
                                            header=header,
                                            onConfirm = {
                                                bottomSheetActive.value = false
                                                editPurchasedProductFormVM.setDefaultState()
                                                startDestination.value = ModalBottomSheetNavigation.AddPurchasedProductRoute
                                                purchasedProductListVM.setLoading(false)
                                            }
                                        )
                                    }
                                )
                            },
                            onClickAddProduct = {
                                navController.navigate(route=ModalBottomSheetNavigation.AddProductRoute)

                            },
                            onDismiss = {
                                scaffoldScope.launch {
                                    purchasedProductListVM.setLoading(false)
                                    bottomSheetActive.value = false
                                    startDestination.value = ModalBottomSheetNavigation.AddPurchasedProductRoute
                                    editPurchasedProductFormVM.setDefaultState()
                                    editPurchasedProductFormVM.clearErrors()
                                }

                            },
                            onClickAddMeasurementUnit = {
                                navController.navigate(route=ModalBottomSheetNavigation.AddMeasurementUnitRoute)

                            }
                        )
                    }
                    composable(route=ModalBottomSheetNavigation.AddMeasurementUnitRoute){
                        val addMeasurementUnitVM = hiltViewModel<AddMeasurementUnitViewModel>()
                        AddMeasurementUnitFormComponent(
                            viewModel = addMeasurementUnitVM,
                            onConfirm = {
                                measurementUnitsListVM.toAddMeasurementUnit(
                                    it,
                                    onSuccess = { header ->
                                        dialogMessageVM.setSuccessDialogState(
                                            header=header,
                                            onConfirm = {
                                                bottomSheetActive.value = false
                                                addMeasurementUnitVM.setDefaultState()
                                            }
                                        )
                                        navController.navigate(route=ModalBottomSheetNavigation.AddPurchasedProductRoute)
                                        startDestination.value = ModalBottomSheetNavigation.AddPurchasedProductRoute



                                    },
                                    onError = {header, errors ->
                                        dialogMessageVM.setErrorDialogState(
                                            header=header,
                                            errors =errors,
                                            onConfirm = {})
                                    },
                                )
                            },
                            onDismiss = {
                                navController.navigate(route=ModalBottomSheetNavigation.AddPurchasedProductRoute)
                            }
                        )

                    }
                    composable(route = ModalBottomSheetNavigation.AddCategoryRoute){
                        val addCategoryFormVM = hiltViewModel<AddCategoryFormViewModel>()
                        AddCategoryFormComponent(
                            viewModel = addCategoryFormVM,
                            onConfirm = {
                                categoryListVM.toAddCategory(
                                    it,
                                    onSuccess = { header ->
                                        dialogMessageVM.setSuccessDialogState(
                                            header=header,
                                            onConfirm = {
                                                bottomSheetActive.value = false
                                                addCategoryFormVM.setDefaultState()
                                            }
                                        )
                                        navController.navigate(route=ModalBottomSheetNavigation.AddProductRoute)
                                    },
                                    onError = {header, errors ->
                                        dialogMessageVM.setErrorDialogState(
                                            header=header,
                                            errors =errors,
                                            onConfirm = {})
                                    }
                                )
                                        },
                            onDismiss = {navController.navigate(route=ModalBottomSheetNavigation.AddProductRoute)}

                        )
                    }
                }
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
}
