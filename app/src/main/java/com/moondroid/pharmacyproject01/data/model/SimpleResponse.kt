package com.moondroid.pharmacyproject01.data.model

import androidx.annotation.Keep


@Keep
data class SimpleResponse(
    val code: Int = 0,
    val message: String = ""
)