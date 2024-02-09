package com.mypurchasedproduct.presentation.screens.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mypurchasedproduct.domain.usecases.CategoryUseCase
import com.mypurchasedproduct.domain.usecases.ProductUseCase
import com.mypurchasedproduct.presentation.state.AddCategoryState
import com.mypurchasedproduct.presentation.state.FindProductsState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
): ViewModel(){
    private val TAG = this.javaClass.simpleName

    var addCategoryState by mutableStateOf(AddCategoryState())
        private set


    fun setActiveAddCategory(isActive: Boolean){
        Log.wtf(TAG, "ON ADD CATEGORY")
        addCategoryState = addCategoryState.copy(
            isActive = isActive
        )

    }

    fun setDefaultState(){
        addCategoryState = AddCategoryState()
    }


    fun addCategory(name:String){
        Log.wtf(TAG, "ADD CATEGORY")
        viewModelScope.launch {
            addCategoryState = addCategoryState.copy(
                isLoading = true
            )
            val response = categoryUseCase.addCategory(name.lowercase())
            when(response){
                is NetworkResult.Success ->{
                    addCategoryState = addCategoryState.copy(
                        isLoading = false,
                        isSuccess = true,
                        addedCategory = response.data
                    )
                }
                is NetworkResult.Error ->{
                    addCategoryState = addCategoryState.copy(
                        isLoading = false,
                        isError = true,
                        error = response.message
                    )
                }
            }

        }

    }
}