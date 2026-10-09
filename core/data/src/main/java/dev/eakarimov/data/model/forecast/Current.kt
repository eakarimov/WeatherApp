package dev.eakarimov.data.model.forecast

import dev.eakarimov.data.model.forecast.common.Condition

data class Current(
    val tempC: Int,
    val condition: Condition,
    val windKph: Double,
    val pressureMb: Int,
    val precipMm: Double,
    val humidity: Int,
)
