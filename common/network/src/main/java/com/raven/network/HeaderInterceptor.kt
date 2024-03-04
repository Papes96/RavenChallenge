package com.raven.network

import com.raven.network.AuthKeys.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain.request()) {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY) //TODO: For security reason this value should be in local.properties
            .build()

        val request = originalRequest.newBuilder().url(url).build()
        return@with chain.proceed(request)
    }
}

object AuthKeys {
    const val API_KEY = "api-key"
}
