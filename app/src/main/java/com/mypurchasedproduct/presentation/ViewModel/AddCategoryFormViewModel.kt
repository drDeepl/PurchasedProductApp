package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.domain.model.CategoryModel
import com.mypurchasedproduct.domain.usecases.CategoryUseCase
import com.mypurchasedproduct.presentation.state.AddCategoryState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryFormViewModel @Inject constructor(
): ViewModel(){
    private val TAG = this.javaClass.simpleName


    private val _state = MutableStateFlow(AddCategoryState())
    val state = _state.asStateFlow()

    private val _errors = MutableStateFlow(mutableListOf<String>())
    val errors  = _errors.asStateFlow()

    private val _categoryModel = MutableStateFlow(CategoryModel())
    val categoryModel = _categoryModel.asStateFlow()




    fun setDefaultState(){
        viewModelScope.launch {
            Log.d(TAG, "SET DEFAULT STATE")
            _state.update{AddCategoryState()}
        }
    }

    fun setDefaultCategoryModel(){
        viewModelScope.launch {
            Log.d(TAG, "SET DEFAULT CATEGORY MODEL")
            _categoryModel.update { CategoryModel() }
        }
    }

    fun setLoading(isLoading: Boolean){
        viewModelScope.launch {
            Log.d(TAG, "SET LOADING")
            _state.update{state ->
                state.copy(isLoading = true)
            }
        }
    }

    fun setCategoryName(name:String){
        viewModelScope.launch {
            Log.d(TAG, "SET CATEGORY NAME")
            _categoryModel.update { categoryModel ->
                categoryModel.copy(
                    name = name
                )
            }
        }
    }

    fun setErrors(errors: MutableList<String>){
            Log.d(TAG, "SET ERRORS")
            _errors.update { errors }
    }

    fun validate(): MutableList<String>{
        val validateErrors = mutableListOf<String>()
        if(categoryModel.value.name.isEmpty()){
            validateErrors.add("название не может быть пустым")
        }
        return validateErrors
    }

    fun getCategoryModel(): CategoryModel{
        return categoryModel.value
    }
}