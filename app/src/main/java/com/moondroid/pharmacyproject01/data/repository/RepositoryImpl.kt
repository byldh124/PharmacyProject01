package com.moondroid.pharmacyproject01.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moondroid.pharmacyproject01.common.SERVICE_KEY
import com.moondroid.pharmacyproject01.data.ApiService
import com.moondroid.pharmacyproject01.data.PagingSource
import com.moondroid.pharmacyproject01.data.model.AddressItem
import com.moondroid.pharmacyproject01.data.model.DetailItem
import com.moondroid.pharmacyproject01.data.model.LocationItem
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.domain.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val apiService: ApiService
) : Repository {
    companion object {
        const val RESULT_OK = "00"
    }

    override fun getListByAddress(city: String, district: String): Flow<PagingData<AddressItem>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { PagingSource(retrofit, SERVICE_KEY, city, district) }
        ).flow
    }

    override suspend fun getListByLocation(
        numOfRows: Int,
        lng: Double,
        lat: Double,
    ): Flow<ApiResult<List<LocationItem>>> {
        return flow {
            try {
                apiService.getListByLocation(SERVICE_KEY, 1, numOfRows, lng, lat).run {
                    if (header.resultCode == RESULT_OK) {
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

    override suspend fun getDetail(hpid: String): Flow<ApiResult<DetailItem>> {
        return flow {
            try {
                apiService.getDetail(SERVICE_KEY, hpid, 1, 1).run {
                    if (header.resultCode == RESULT_OK) {
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