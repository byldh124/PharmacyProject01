package com.moondroid.pharmacyproject01.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moondroid.pharmacyproject01.common.SERVICE_KEY
import com.moondroid.pharmacyproject01.data.ApiService
import com.moondroid.pharmacyproject01.data.MyApiService
import com.moondroid.pharmacyproject01.data.PagingSource
import com.moondroid.pharmacyproject01.data.SlackApiService
import com.moondroid.pharmacyproject01.data.model.request.PostMessageRequest
import com.moondroid.pharmacyproject01.data.model.resopnse.AddressItem
import com.moondroid.pharmacyproject01.data.model.resopnse.DetailItem
import com.moondroid.pharmacyproject01.data.model.resopnse.LocationItem
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.domain.model.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mApiService: MyApiService,
    private val slackApiService: SlackApiService,
) : Repository {
    companion object {
        const val RESULT_OK = "00"
    }

    override suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): Flow<Int> {
        return flow {
            val response = mApiService.checkAppVersion(versionCode, versionName, packageName)
            emit(response.code)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getListByAddress(city: String, district: String): Flow<PagingData<AddressItem>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { PagingSource(apiService, SERVICE_KEY, city, district) }
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

    override suspend fun postMessage(token: String, message: String) {
        slackApiService.postMessage(token, PostMessageRequest("C06H55PNMSN", message))
    }
}