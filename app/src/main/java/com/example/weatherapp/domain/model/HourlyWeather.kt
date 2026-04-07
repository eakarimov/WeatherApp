package com.example.weatherapp.domain.model

data class HourlyWeather(
    val hour: Int,
    val hourText: String,
    val temperature: Int,
    val iconUrl: String,
    val chanceOfRain: Int,
)
