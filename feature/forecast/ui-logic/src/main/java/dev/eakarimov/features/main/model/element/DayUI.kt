package dev.eakarimov.features.main.model.element

import dev.eakarimov.features.main.model.element.common.ConditionUI
import java.time.LocalDate

data class DayUI(
    val date: LocalDate,
    val maxTempC: Int,
    val minTempC: Int,
    val chanceOfRain: Int,
    val condition: ConditionUI,
    val hours: List<HourUI>,
)
