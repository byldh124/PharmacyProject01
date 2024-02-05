package com.moondroid.pharmacyproject01

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.moondroid.pharmacyproject01.common.NetworkConnection
import com.moondroid.pharmacyproject01.common.Preferences
import com.moondroid.pharmacyproject01.common.firebase.FBAnalyze
import com.moondroid.pharmacyproject01.domain.Repository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        NetworkConnection.init(applicationContext)
        FBAnalyze.init(applicationContext)
        Preferences.init(applicationContext)

        if (!BuildConfig.DEBUG) {
            postMessage()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun postMessage() {
        CoroutineScope(Dispatchers.Main).launch {
            val prefix = if (Preferences.getBoolean("first_open", true)) {
                Preferences.putBoolean("first_open", false)
                "[신규 회원]"
            } else {
                "[기존 회원]"
            }
            val appName = getString(R.string.app_name)
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            repository.postMessage("$prefix $appName 사용자가 들어왔습니다. $date")
        }
    }
}
