package com.moondroid.pharmacyproject01.data

data class Response(
    val code: Int = 0,
    val message: String = "",
    val result: List<String> = emptyList(),
)

data class SimpleResponse(
    val code: Int = 0,
    val message: String = "",
)