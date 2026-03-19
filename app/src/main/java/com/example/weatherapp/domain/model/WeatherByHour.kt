package com.example.weatherapp.domain.model

data class WeatherByHour(
    val timestamp: Long,
    val temp: Double,
    val iconIrl: String,
)
