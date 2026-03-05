package com.example.weatherapp

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
)

data class Current(
    val temp_c: Double,
    val condition: Condition,
)

data class Forecast(
    val forecastday: List<ForecastDay>,
)

data class Condition(
    val text: String,
)

data class ForecastDay(
    val day: Day,
)

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
)