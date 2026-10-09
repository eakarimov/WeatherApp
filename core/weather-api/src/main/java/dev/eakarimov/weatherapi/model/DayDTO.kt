package dev.eakarimov.weatherapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DayDTO(
    @SerialName("maxtemp_c") val maxTempC: Double,
    @SerialName("mintemp_c") val minTempC: Double,
    @SerialName("daily_chance_of_rain") val dailyChanceOfRain: Int,
    @SerialName("condition") val condition: ConditionDTO,
)
