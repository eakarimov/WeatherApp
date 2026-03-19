package com.example.weatherapp.domain.model

data class WeatherCurrent(
    val timestamp: Long,
    val temp: Double,
    val condition: String,
    val iconUrl: String,
    val tempMax: Double,
    val tempMin: Double,
)
