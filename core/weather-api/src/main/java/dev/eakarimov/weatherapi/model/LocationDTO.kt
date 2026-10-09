package dev.eakarimov.weatherapi.model

import dev.eakarimov.weatherapi.util.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class LocationDTO(
    @SerialName("name") val name: String,
    @SerialName("localtime") @Serializable(LocalDateTimeSerializer::class) val localtime: LocalDateTime,
)
