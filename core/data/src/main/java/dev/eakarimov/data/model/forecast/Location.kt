package dev.eakarimov.data.model.forecast

import java.time.LocalDateTime

data class Location(
    val name: String,
    val dateTime: LocalDateTime,
)