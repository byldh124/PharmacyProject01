package com.moondroid.pharmacyproject01.common.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics


object FBAnalyze {
    private lateinit var analytics: FirebaseAnalytics

    fun init(context: Context) {
        analytics = FirebaseAnalytics.getInstance(context)
    }

    fun setProperty(id: String) {
        analytics.setUserId(id)
    }

    fun logEvent(event: String) {
        analytics.logEvent(event, null)
    }

    fun logEvent(event: String, params: Bundle) {
        analytics.logEvent(event, params)
    }
}