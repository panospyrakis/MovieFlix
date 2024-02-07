package com.spyrakis.movieflix.data.repositories.sources.remote.config

import com.spyrakis.movieflix.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOriginal = chain.request();
        val requestToReturn = requestOriginal.newBuilder()
            .addHeader("Authorization", BuildConfig.API_KEY)
            .build()

        return chain.proceed(requestToReturn)
    }
}