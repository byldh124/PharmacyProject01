package com.moondroid.pharmacyproject01.data.repository

import com.moondroid.pharmacyproject01.data.ApiService
import com.moondroid.pharmacyproject01.data.model.LocationItem
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.domain.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

const val SERVICE_KEY =
    "bSPPNxDRiwAbHHyVwugShfNzehraTySYWyV8Miumx3bO4Jh6LQLu1cYO6dE4arWvo6gB2BL+FCtNCyZnhsz/zQ=="

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {
    override suspend fun getListByLocation(
        numOfRows: Int,
        lng: Double,
        lat: Double,
    ): Flow<ApiResult<List<LocationItem>>> {
        return flow {
            try {
                apiService.getListByLocation(SERVICE_KEY, 1, numOfRows, lng, lat).run {
                    if (header.resultCode == "00") {
                        emit(ApiResult.Success(body.items.item))
                    } else {
                        emit(ApiResult.Fail(header.resultMsg))
                    }
                }
            } catch (e: Exception) {
                emit(ApiResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)

    }
}