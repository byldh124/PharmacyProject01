package com.moondroid.pharmacyproject01.di

import android.util.Log
import com.moondroid.pharmacyproject01.BuildConfig
import com.moondroid.pharmacyproject01.data.ApiService
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/"

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
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        tikXmlConverterFactory: TikXmlConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(tikXmlConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}