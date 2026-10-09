package dev.eakarimov.weatherapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO<E>(
    @SerialName("location") val location: LocationDTO,
    @SerialName("current") val current: CurrentDTO,
    @SerialName("forecast") val forecast: E,
)
