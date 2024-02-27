package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.domain.model.CategoryModel
import com.mypurchasedproduct.domain.usecases.CategoryUseCase
import com.mypurchasedproduct.domain.usecases.ProductUseCase
import com.mypurchasedproduct.presentation.state.CategoryListState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val categoryUseCase: CategoryUseCase,
): ViewModel() {

    private val TAG: String = this.javaClass.simpleName


    private val _state = MutableStateFlow(CategoryListState())
    val state = _state.asStateFlow()

    private val _categories = MutableStateFlow<MutableList<CategoryResponse>>(mutableListOf())
    val categories = _categories.asStateFlow()

    private val _errors = MutableStateFlow<MutableList<String>>(mutableListOf())
    val errors = _errors.asStateFlow()


    init{
        viewModelScope.launch {
            Log.d(TAG, "INITIALIZE CATEGORIES")
            findCategories()
        }
    }


    fun toAddCategory(
        categoryModel: CategoryModel,
        onSuccess: (header: String)-> Unit,
        onError: (header: String, errors: MutableList<String>) -> Unit
    ){
        Log.wtf(TAG, "ADD CATEGORY")
        viewModelScope.launch {
            _state.update {state ->
                state.copy(
                    isLoading = true
                )
            }
            val networkResult = categoryUseCase.addCategory(categoryModel)
            when(networkResult){
                is NetworkResult.Success ->{
                    _state.update { state ->
                        state.copy(isLoading = false)
                    }
                    networkResult.data?.let {
                        _categories.value.add(0,it)
                    }
                    onSuccess("Категория успешно добавлена!")
                }
                is NetworkResult.Error ->{
                    errors.value.add(networkResult.message.toString())
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                        )
                    }
                    onError("ошибка при добавлении категории", errors.value)
                }
            }

        }

    }


    fun findCategories(){
        viewModelScope.launch {
            Log.wtf(TAG, "FIND CATEGORIES")

            _state.update { state ->
                state.copy(isLoading = true)
            }

            val networkResult = productUseCase.getCategories()
            when(networkResult){
                is NetworkResult.Success -> {

                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isSuccess = true,
                        )
                    }
                    _categories.update { networkResult.data ?: mutableListOf() }

                }
                is NetworkResult.Error ->{
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                        )
                    }
                    errors.value.add(networkResult.message.toString())

                }
            }


        }
    }

}