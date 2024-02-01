package com.moondroid.pharmacyproject01.data

import com.moondroid.pharmacyproject01.data.model.SimpleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiService {
    @GET("checkVersion.php")
    suspend fun checkAppVersion(
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String,
        @Query("packageName") packageName: String,
    ): SimpleResponse
}