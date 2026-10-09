package dev.eakarimov.features.main.model.element

import dev.eakarimov.features.main.model.element.common.ConditionUI
import java.time.LocalDateTime

data class HourUI(
    val dateTime: LocalDateTime,
    val tempC: Int,
    val condition: ConditionUI,
    val chanceOfRain: Int,
)
