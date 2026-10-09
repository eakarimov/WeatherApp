package dev.eakarimov.features.main.model

import dev.eakarimov.features.main.model.element.CurrentUI
import dev.eakarimov.features.main.model.element.DayUI
import dev.eakarimov.features.main.model.element.LocationUI

data class ForecastUI(
    val location: LocationUI,
    val current: CurrentUI,
    val days: List<DayUI>,
)
