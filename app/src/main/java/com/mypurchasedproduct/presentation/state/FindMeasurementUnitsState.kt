package com.mypurchasedproduct.presentation.state

import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse

data class FindMeasurementUnitsState(
    var isUpdating: Boolean = true,
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var isError: Boolean = false,
    var error: String? = null,
    var measurementUnits: List<MeasurementUnitResponse>? = null,
)
