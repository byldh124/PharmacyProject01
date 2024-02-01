package com.moondroid.pharmacyproject01.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moondroid.pharmacyproject01.common.debug
import com.moondroid.pharmacyproject01.data.model.AddressItem
import retrofit2.Retrofit

class PagingSource(
    private val retrofit: Retrofit,
    private val serviceKey: String,
    private val city: String,
    private val district: String,
) : PagingSource<Int, AddressItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AddressItem> {
        debug("load")
        val page = params.key ?: 1
        return try {
            val service = retrofit.create(ApiService::class.java)
            val response = service.getListByAddress(
                serviceKey = serviceKey,
                city = city,
                district = district,
                pageNo = page,
                numOfRows = params.loadSize,
            )
            Log.e("TAG", "RESPONSE :$response")
            val items = response.body.items.item
            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
            )
        } catch (e: Exception) {
            debug("e : $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AddressItem>): Int? {
        debug("getRefreshKey")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}