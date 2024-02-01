package com.moondroid.pharmacyproject01.data

import com.moondroid.pharmacyproject01.data.model.AddressResponse
import com.moondroid.pharmacyproject01.data.model.DetailResponse
import com.moondroid.pharmacyproject01.data.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("getParmacyListInfoInqire")
    suspend fun getListByAddress(
        @Query("serviceKey") serviceKey: String,
        @Query("Q0") city: String,
        @Query("Q1") district: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
    ): AddressResponse

    @GET("getParmacyLcinfoInqire")
    suspend fun getListByLocation(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("WGS84_LON") lng: Double,
        @Query("WGS84_LAT") lat: Double,
    ): LocationResponse

    @GET("getParmacyBassInfoInqire")
    suspend fun getDetail(
        @Query("serviceKey") serviceKey: String,
        @Query("HPID") hpid: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") packageName: Int,
    ): DetailResponse
}