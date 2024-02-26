package com.mypurchasedproduct.data.repository

import android.util.Log
import com.mypurchasedproduct.data.remote.RemoteDataSource
import com.mypurchasedproduct.data.remote.model.request.AddMeasurementUnitRequest
import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.presentation.utils.BaseApiResponse
import com.mypurchasedproduct.presentation.utils.NetworkResult
import javax.inject.Inject

class MeasurementUnitRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): BaseApiResponse(){

    private  val TAG: String = this.javaClass.simpleName

    suspend fun getMeasurementUnits(): NetworkResult<MutableList<MeasurementUnitResponse>>{
        Log.d(TAG, "GET MEASUREMENT UNITS")
        return safeApiCall { remoteDataSource.getMeasurementUnits() }
    }

    suspend fun toAddMeasurementUnit(addMeasurementUnitRequest: AddMeasurementUnitRequest): NetworkResult<MeasurementUnitResponse>{
        Log.d(TAG, "TO ADD MEASUREMENT UNIT")
        return safeApiCall { remoteDataSource.toAddMeasurementUnit(addMeasurementUnitRequest) }
    }
}