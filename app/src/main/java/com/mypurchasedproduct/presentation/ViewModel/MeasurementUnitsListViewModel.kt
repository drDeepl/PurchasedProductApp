package com.mypurchasedproduct.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.domain.model.MeasurementUnitModel
import com.mypurchasedproduct.domain.usecases.MeasurementUnitUseCase
import com.mypurchasedproduct.presentation.state.MeasurementUnitsListState
import com.mypurchasedproduct.presentation.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasurementUnitsListViewModel @Inject constructor(
    private val measurementUnitUseCase: MeasurementUnitUseCase
): ViewModel() {
    private val TAG: String = this.javaClass.simpleName

    private val _state = MutableStateFlow(MeasurementUnitsListState(false,false, "", null))
    val state = _state.asStateFlow()

    private val _measurementUnits: MutableStateFlow<MutableList<MeasurementUnitResponse>> = MutableStateFlow(mutableListOf())

    val measurementUnits = _measurementUnits.asStateFlow()

    private val _errors = MutableStateFlow(mutableListOf<String>())
    val errors = _errors.asStateFlow()



    init{
        viewModelScope.launch {
            Log.d(TAG, "INIT")
            getMeasurementUnits()
        }
    }


    fun setSelectedMeasurementUnit(measurementUnit: MeasurementUnitResponse){
        viewModelScope.launch {
            Log.d(TAG, "SET SELECTED MEASUREMENT UNIT")
            _state.update { state ->
                state.copy(selectedMeasurementUnit = measurementUnit)
            }
        }
    }

    fun getMeasurementUnits(){
        viewModelScope.launch {
            Log.d(TAG, "GET MEASUREMENT UNITS")

            _state.update {state ->
                state.copy(isLoading = true)
            }

            val result: NetworkResult<MutableList<MeasurementUnitResponse>> = measurementUnitUseCase.getMeasurementUnits()
            when(result){
                is NetworkResult.Success -> {
                    _measurementUnits.update { result.data ?: mutableListOf() }
                    _state.update {state ->
                        state.copy(isLoading = false)
                    }
                }
                is NetworkResult.Error ->{
                    _state.update {state ->
                        state.copy(
                            isLoading = false,
                            isError = true,
                            error= result.message.toString()
                        )
                    }
                }
            }
        }
    }

    fun toAddMeasurementUnit(
        measurementUnitModel: MeasurementUnitModel,
        onSuccess: (header: String)-> Unit,
        onError: (header: String, errors: MutableList<String>) -> Unit
    ){
        viewModelScope.launch {
            Log.d(TAG, "TO ADD MEASUREMENT UNIT ")
            _state.update { state ->
                state.copy(isLoading = true)
            }
            val result = measurementUnitUseCase.toAddMeasurementUnit(measurementUnitModel)
            when(result){
                is NetworkResult.Success ->{
                    result.data?.let{
                        _measurementUnits.value.add(it)
                    }
                    onSuccess("Еденица измерения успешно добавлена!")
                    _state.update { state ->
                        state.copy(isLoading = false)
                    }
                }
                is NetworkResult.Error ->{
                    _state.update { state ->
                        onError("ошибка при добавлении единицы измерения", errors.value)
                        state.copy(
                            isError = true,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}




