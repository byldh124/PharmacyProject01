package com.moondroid.pharmacyproject01.data

import com.moondroid.pharmacyproject01.common.SLACK_TOKEN
import com.moondroid.pharmacyproject01.data.model.request.PostMessageRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SlackApiService {
    @POST("chat.postMessage")
    @Headers("Authorization: Bearer xoxb-$SLACK_TOKEN")
    suspend fun postMessage(
        @Body postMessageRequest: PostMessageRequest,
    ): Response<Unit>
}