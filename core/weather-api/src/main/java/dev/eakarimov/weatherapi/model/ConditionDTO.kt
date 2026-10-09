package dev.eakarimov.weatherapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConditionDTO(
    @SerialName("text") val text: String,
    @SerialName("icon") val icon: String,
)
