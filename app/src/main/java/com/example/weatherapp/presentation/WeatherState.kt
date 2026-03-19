package com.example.weatherapp.presentation

import com.example.weatherapp.domain.model.Weather

data class WeatherState (
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String = ""
)