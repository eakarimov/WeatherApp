package dev.eakarimov.weatherapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    @SerialName("forecastday") val forecastDay: List<ForecastDayDTO>,
)
