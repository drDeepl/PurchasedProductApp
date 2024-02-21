package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mypurchasedproduct.presentation.state.ModalSheetFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ModalSheetFormViewModel @Inject constructor(): ViewModel() {

    private val TAG: String = this.javaClass.simpleName

    private val _state = MutableStateFlow(ModalSheetFormState(false, ""))

    val state = _state.asStateFlow()

    fun setActiveModalSheet(isActive: Boolean){
        Log.d(TAG, "SET ACTIVE MODAL SHEET")
        _state.update { state ->
            state.copy(isActive = isActive)
        }
    }

    fun setCurrentRouteModalSheet(routeName: String){
        Log.d(TAG, "SET CURRENT ROUTE")
        _state.update { state ->
            state.copy(currentRoute = routeName)
        }
    }
}