package com.moondroid.pharmacyproject01.common.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics

object FBCrash {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    fun setProperty(userId: String) {
        crashlytics.setUserId(userId)
    }

    fun report(t: Throwable) {
        crashlytics.log(t.stackTraceToString())
    }
}