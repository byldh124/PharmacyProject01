package com.moondroid.pharmacyproject01.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.moondroid.pharmacyproject01.BuildConfig
import com.moondroid.pharmacyproject01.data.ApiService
import com.moondroid.pharmacyproject01.data.MyApiService
import com.moondroid.pharmacyproject01.data.SlackApiService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/"
    private const val MY_URL = "http://moondroid.dothome.co.kr/imagePuzzle/"
    private const val SLACK_URL = "https://slack.com/api/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor {
            val log = try {
                JSONObject(it).toString()
            } catch (e: JSONException) {
                //URLDecoder.decode(it, "UTF-8")
                it
            }
            Log.d("HttpClient", log)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }


    @Provides
    @Singleton
    fun provideConverterFactory(): TikXmlConverterFactory {
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        return TikXmlConverterFactory.create(parser)
    }

    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient,
        tikXmlConverterFactory: TikXmlConverterFactory,
    ): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(tikXmlConverterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    @Singleton
    @Provides
    fun provideMyApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): MyApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(MY_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(MyApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideSlackApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): SlackApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(SLACK_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(SlackApiService::class.java)
    }
}