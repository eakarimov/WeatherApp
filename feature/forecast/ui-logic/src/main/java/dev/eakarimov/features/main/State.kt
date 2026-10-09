package dev.eakarimov.features.main

import dev.eakarimov.features.main.model.ForecastUI

sealed class State(open val forecast: ForecastUI?) {

    data object None : State(forecast = null)
    data object Loading : State(forecast = null)
    data class Success(override val forecast: ForecastUI) : State(forecast)
    data object Error : State(forecast = null)
}
