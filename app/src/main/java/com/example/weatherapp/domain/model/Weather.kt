package com.example.weatherapp.domain.model

data class Weather (
    val location: Location,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
)