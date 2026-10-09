package dev.eakarimov.weatherapi.model

import dev.eakarimov.weatherapi.util.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ForecastDayDTO(
    @SerialName("date") @Serializable(LocalDateSerializer::class) val date: LocalDate,
    @SerialName("day") val day: DayDTO,
    @SerialName("hour") val hour: List<HourDTO>,
)
