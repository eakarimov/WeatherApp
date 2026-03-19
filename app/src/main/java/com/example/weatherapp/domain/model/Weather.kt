package com.example.weatherapp.domain.model

data class Weather (
    val current: WeatherCurrent,
    val byHours: List<WeatherByHour>,
)