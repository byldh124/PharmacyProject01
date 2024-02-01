package com.moondroid.pharmacyproject01

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.moondroid.pharmacyproject01.common.NetworkConnection
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        NetworkConnection.init(applicationContext)
    }
}
