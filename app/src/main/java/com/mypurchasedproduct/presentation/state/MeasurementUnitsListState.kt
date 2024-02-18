package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse

data class MeasurementUnitsListState(
    val isLoading:Boolean,
    val isError: Boolean,
    val error: String,
    val selectedMeasurementUnit: MeasurementUnitResponse?
)
