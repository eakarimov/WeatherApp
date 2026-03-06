package com.example.weatherapp

data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast,
)

data class Location(
    val localtime: String,
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
    val icon: String,
)

data class ForecastDay(
    val day: Day,
    val hour: List<Hour>,
)

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
)

data class Hour(
    val temp_c: Double,
    val condition: Condition,
)