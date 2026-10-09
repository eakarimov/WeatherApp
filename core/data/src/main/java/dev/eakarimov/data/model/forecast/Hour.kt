package dev.eakarimov.data.model.forecast

import dev.eakarimov.data.model.forecast.common.Condition
import java.time.LocalDateTime

data class Hour(
    val dateTime: LocalDateTime,
    val tempC: Int,
    val condition: Condition,
    val chanceOfRain: Int,
)
