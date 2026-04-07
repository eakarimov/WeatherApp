package com.example.weatherapp.domain.model

data class Location(
    val name: String,
    val currentHour: Int,
    val dayOfWeek: String,
    val monthAndDay: String,
)
