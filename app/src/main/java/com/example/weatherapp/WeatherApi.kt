package com.example.weatherapp

import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    @GET("forecast.json?key=${BuildConfig.API_KEY}&q=55.7561,52.4289&lang=en&days=2")
    suspend fun getWeather(): Response<WeatherResponse>
}