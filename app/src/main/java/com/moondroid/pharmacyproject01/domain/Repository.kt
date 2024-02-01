package com.moondroid.pharmacyproject01.domain

import androidx.paging.PagingData
import com.moondroid.pharmacyproject01.data.model.AddressItem
import com.moondroid.pharmacyproject01.data.model.DetailItem
import com.moondroid.pharmacyproject01.data.model.LocationItem
import com.moondroid.pharmacyproject01.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getListByAddress(
        city: String,
        district: String
    ): Flow<PagingData<AddressItem>>

    suspend fun getListByLocation(
        numOfRows: Int,
        lng: Double,
        lat: Double,
    ): Flow<ApiResult<List<LocationItem>>>

    suspend fun getDetail(hpid: String): Flow<ApiResult<DetailItem>>
}