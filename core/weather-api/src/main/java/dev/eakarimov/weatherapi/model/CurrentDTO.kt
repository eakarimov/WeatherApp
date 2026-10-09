package dev.eakarimov.weatherapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDTO(
    @SerialName("temp_c") val tempC: Double,
    @SerialName("condition") val condition: ConditionDTO,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("pressure_mb") val pressureMb: Double,
    @SerialName("precip_mm") val precipMm: Double,
    @SerialName("humidity") val humidity: Int,
)
