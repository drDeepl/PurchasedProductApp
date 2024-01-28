package com.mypurchasedproduct.domain.usecases

import com.mypurchasedproduct.data.repository.MeasurementUnitRepository
import javax.inject.Inject

class MeasurementUnitUseCase @Inject constructor(
    private val measurementUnitRepository: MeasurementUnitRepository
) {

    suspend fun getMeasurementUnits() = measurementUnitRepository.getMeasurementUnits()
}