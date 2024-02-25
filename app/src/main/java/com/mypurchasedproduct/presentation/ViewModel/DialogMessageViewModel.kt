package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.presentation.state.ErrorDialogState
import com.mypurchasedproduct.presentation.state.SuccessDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogMessageViewModel @Inject constructor(): ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    private val _successDialogState = MutableStateFlow(SuccessDialogState())
    val successDialogState = _successDialogState.asStateFlow()

    private val _errorDialogState = MutableStateFlow(ErrorDialogState())
    val errorDialogState = _errorDialogState.asStateFlow()


    fun setSuccessDialogState(header: String, onConfirm: () -> Unit){
        viewModelScope.launch {
            Log.d(TAG, "SET SUCCESS DIALOG STATE")
            _successDialogState.update { successDialogState ->
                successDialogState.copy(
                    isActive = true,
                    header = header,
                    onConfirm = onConfirm
                )
            }
        }
    }

    fun setErrorDialogState(header: String, errors: MutableList<String>, onConfirm: () -> Unit){
        viewModelScope.launch {
            Log.d(TAG, "SET SUCCESS DIALOG STATE")
            _errorDialogState.update { successDialogState ->
                successDialogState.copy(
                    isActive = true,
                    header = header,
                    errors = errors,
                    onConfirm = onConfirm
                )
            }
        }
    }

    fun setDefaultSuccessState(){
        viewModelScope.launch {
            Log.d(TAG, "SET DEFUALT SUCCESS STATE")
            _successDialogState.update { SuccessDialogState() }
        }
    }

    fun setDefaultErrorState(){
        viewModelScope.launch {
            Log.d(TAG, "SET DEFUALT SUCCESS STATE")
            _errorDialogState.update { ErrorDialogState() }
        }
    }

}