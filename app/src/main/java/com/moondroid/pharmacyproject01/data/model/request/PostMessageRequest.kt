package com.moondroid.pharmacyproject01.data.model.request

data class PostMessageRequest(
    private val channel: String,
    private val text: String
)
