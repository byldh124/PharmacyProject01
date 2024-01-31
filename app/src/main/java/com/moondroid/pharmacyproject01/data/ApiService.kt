package com.moondroid.pharmacyproject01.data

import com.moondroid.pharmacyproject01.data.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("getParmacyListInfoInqire")
    suspend fun getListByQuery(
        @Query("serviceKey") versionCode: Int,
        @Query("pageNo") versionName: String,
        @Query("numOfRows") packageName: String,
    ): SimpleResponse

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
        @Query("serviceKey") versionCode: Int,
        @Query("pageNo") versionName: String,
        @Query("numOfRows") packageName: String,
    ): SimpleResponse

    @GET("getParmacyFullDown")
    suspend fun getFullDown(
        @Query("serviceKey") versionCode: Int,
        @Query("pageNo") versionName: String,
        @Query("numOfRows") packageName: String,
    ): SimpleResponse

}