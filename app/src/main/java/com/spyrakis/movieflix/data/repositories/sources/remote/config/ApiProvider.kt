package com.spyrakis.movieflix.data.repositories.sources.remote.config

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import timber.log.Timber
import java.util.concurrent.TimeUnit

object ApiProvider {
    private const val TIMEOUT = 10L
    private const val URL = "https://api.themoviedb.org/"

    private val loggingInterceptor by lazy(mode = LazyThreadSafetyMode.NONE) {
        HttpLoggingInterceptor {
            Timber.d("logging: $it")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val gson by lazy(mode = LazyThreadSafetyMode.NONE) {
        GsonBuilder().create()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(AuthInterceptor())
            addInterceptor(loggingInterceptor)
        }.build()
    }


    val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder().apply {
            baseUrl(URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttpClient)
        }.build()
    }


    inline fun <reified T> getApi(): T = retrofit.create<T>()
}