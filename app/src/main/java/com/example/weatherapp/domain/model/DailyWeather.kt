package com.example.weatherapp.domain.model

data class DailyWeather(
    val timestamp: Long,
    val dayOfWeek: String,
    val minTemp: Int,
    val maxTemp: Int,
    val iconUrl: String,
    val chanceOfRain: Int,
)
