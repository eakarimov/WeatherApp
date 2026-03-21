package com.example.weatherapp.presentation

import com.example.weatherapp.domain.error.AppException
import com.example.weatherapp.domain.model.Weather

data class WeatherState (
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: AppException? = null,
)