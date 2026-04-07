package com.example.weatherapp.data.remote

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.dto.WeatherDTO
import retrofit2.http.GET

interface WeatherApi {

    @GET("forecast.json?key=${BuildConfig.API_KEY}&q=55.7561,52.4289&lang=en&days=3")
    suspend fun getWeather(): WeatherDTO
}