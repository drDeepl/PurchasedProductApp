package com.mypurchasedproduct.presentation.screens.ViewModel

import androidx.lifecycle.ViewModel
import com.mypurchasedproduct.data.repository.PurchasedProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchasedProductListViewModel @Inject constructor(
    private val purchasedProductRepository: PurchasedProductRepository
): ViewModel(){
}