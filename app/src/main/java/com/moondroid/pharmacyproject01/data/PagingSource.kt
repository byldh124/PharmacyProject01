package com.moondroid.pharmacyproject01.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moondroid.pharmacyproject01.data.model.resopnse.AddressItem

class PagingSource(
    private val apiService : ApiService,
    private val serviceKey: String,
    private val city: String,
    private val district: String,
) : PagingSource<Int, AddressItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AddressItem> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getListByAddress(
                serviceKey = serviceKey,
                city = city,
                district = district,
                pageNo = page,
                numOfRows = params.loadSize,
            )
            val items = response.body.items.item
            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AddressItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}