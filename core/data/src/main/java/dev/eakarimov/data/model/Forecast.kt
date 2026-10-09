package dev.eakarimov.data.model

import dev.eakarimov.data.model.forecast.Current
import dev.eakarimov.data.model.forecast.Day
import dev.eakarimov.data.model.forecast.Location

data class Forecast(
    val location: Location,
    val current: Current,
    val days: List<Day>,
)
