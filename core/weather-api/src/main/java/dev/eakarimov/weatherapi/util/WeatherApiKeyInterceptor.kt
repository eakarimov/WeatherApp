package dev.eakarimov.weatherapi.util

import okhttp3.Interceptor
import okhttp3.Response

internal class WeatherApiKeyInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .url("${chain.request().url}&key=${apiKey}")
            .build()
        return chain.proceed(request)
    }
}
