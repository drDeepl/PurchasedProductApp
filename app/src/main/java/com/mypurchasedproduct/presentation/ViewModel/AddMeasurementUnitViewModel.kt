package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.domain.model.MeasurementUnitModel
import com.mypurchasedproduct.presentation.state.AddMeasurementUnitState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMeasurementUnitViewModel @Inject constructor(): ViewModel() {

    private val TAG = this.javaClass.simpleName

    private val _state = MutableStateFlow(AddMeasurementUnitState())
    val state = _state.asStateFlow()

    private val _errors = MutableStateFlow(mutableListOf<String>())
    val errors = _errors.asStateFlow()

    private val _measurementUnitModel = MutableStateFlow(MeasurementUnitModel())
    val measurementUnitModel = _measurementUnitModel.asStateFlow()



    fun setLoading(isLoading: Boolean){
        viewModelScope.launch {
            Log.d(TAG, "SET NAME MEASUREMENT UNIT")
            _state.update { state ->
                state.copy(isLoading = isLoading)
            }
        }
    }

    fun setErrors(errors: MutableList<String>){
        viewModelScope.launch {
            Log.d(TAG, "SET NAME MEASUREMENT UNIT")
            _state.update { state ->
                state.copy(isError =  true)
            }
            _errors.update { errors}
        }
    }

    fun setMeasurementUnitName(name: String){
        viewModelScope.launch {
            Log.d(TAG, "SET MEASUREMENT UNIT NAME")
            _measurementUnitModel.update { measurementUnitModel ->
                measurementUnitModel.copy(name=name)
            }
        }
    }

    fun setDefaultState(){
        viewModelScope.launch {
            Log.d(TAG, "SET DEFAULT STATE")
            _state.update { AddMeasurementUnitState() }
            _measurementUnitModel.update { MeasurementUnitModel() }
        }
    }

    fun validate(): MutableList<String>{
        Log.d(TAG, "VALIDATE MEASUREMENT UNIT MODEL")
        val errors = mutableListOf<String>()
        if(measurementUnitModel.value.name.isEmpty()){
            errors.add("название не может быть пустым")
        }
        return errors
    }

    fun getMeasurementUnitModel(): MeasurementUnitModel{
        return measurementUnitModel.value
    }
}