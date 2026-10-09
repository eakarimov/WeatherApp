package dev.eakarimov.weatherapi.model

import dev.eakarimov.weatherapi.util.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class HourDTO(
    @SerialName("time") @Serializable(LocalDateTimeSerializer::class) val time: LocalDateTime,
    @SerialName("temp_c") val tempC: Double,
    @SerialName("condition") val condition: ConditionDTO,
    @SerialName("chance_of_rain") val chanceOfRain: Int,
)
