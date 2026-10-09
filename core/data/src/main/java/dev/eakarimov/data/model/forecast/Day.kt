package dev.eakarimov.data.model.forecast

import dev.eakarimov.data.model.forecast.common.Condition
import java.time.LocalDate

data class Day(
    val date: LocalDate,
    val maxTempC: Int,
    val minTempC: Int,
    val chanceOfRain: Int,
    val condition: Condition,
    val hours: List<Hour>,
)
