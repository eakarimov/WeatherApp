package dev.eakarimov.features.main.model.element

import dev.eakarimov.features.main.model.element.common.ConditionUI

data class CurrentUI(
    val tempC: Int,
    val condition: ConditionUI,
    val windKph: Double,
    val pressureMb: Int,
    val precipMm: Double,
    val humidity: Int,
)