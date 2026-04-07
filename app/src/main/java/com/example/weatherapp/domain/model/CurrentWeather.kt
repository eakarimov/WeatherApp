package com.example.weatherapp.domain.model

data class CurrentWeather(
    val temperature: Int,
    val condition: String,
    val iconUrl: String,
    val windSpeed: Double,
    val precipitation: Double,
    val pressure: Int,
    val humidity: Int,
)
