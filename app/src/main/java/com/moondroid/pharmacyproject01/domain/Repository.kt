package com.moondroid.pharmacyproject01.domain

import com.moondroid.pharmacyproject01.data.model.LocationItem
import com.moondroid.pharmacyproject01.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getListByLocation(
        numOfRows: Int,
        lng: Double,
        lat: Double,
    ): Flow<ApiResult<List<LocationItem>>>
}