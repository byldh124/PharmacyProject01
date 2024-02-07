package com.moondroid.pharmacyproject01.domain

import androidx.paging.PagingData
import com.moondroid.pharmacyproject01.data.model.resopnse.AddressItem
import com.moondroid.pharmacyproject01.data.model.resopnse.DetailItem
import com.moondroid.pharmacyproject01.data.model.resopnse.LocationItem
import com.moondroid.pharmacyproject01.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String
    ): Flow<Int>

    suspend fun getListByAddress(
        city: String,
        district: String,
    ): Flow<PagingData<AddressItem>>

    suspend fun getListByLocation(
        numOfRows: Int,
        lng: Double,
        lat: Double,
    ): Flow<ApiResult<List<LocationItem>>>

    suspend fun getDetail(hpid: String): Flow<ApiResult<DetailItem>>

    suspend fun postMessage(token: String, message: String)
}