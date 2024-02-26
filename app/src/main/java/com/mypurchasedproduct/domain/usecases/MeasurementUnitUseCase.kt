package com.mypurchasedproduct.domain.usecases

import android.net.Network
import com.mypurchasedproduct.data.remote.model.request.AddMeasurementUnitRequest
import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.repository.MeasurementUnitRepository
import com.mypurchasedproduct.domain.model.MeasurementUnitModel
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class MeasurementUnitUseCase @Inject constructor(
    private val measurementUnitRepository: MeasurementUnitRepository
) {

    suspend fun getMeasurementUnits() = measurementUnitRepository.getMeasurementUnits()
    suspend fun toAddMeasurementUnit(measurementUnitModel: MeasurementUnitModel) : NetworkResult<MeasurementUnitResponse>{
        return measurementUnitRepository.toAddMeasurementUnit(
            AddMeasurementUnitRequest(
                measurementUnitModel.name
            )
        )
    }
}