package dev.eakarimov.features.main

import dev.eakarimov.data.RequestResult
import dev.eakarimov.data.model.forecast.Current
import dev.eakarimov.data.model.forecast.Day
import dev.eakarimov.data.model.forecast.Hour
import dev.eakarimov.data.model.forecast.Location
import dev.eakarimov.data.model.forecast.common.Condition
import dev.eakarimov.features.main.model.ForecastUI
import dev.eakarimov.features.main.model.element.CurrentUI
import dev.eakarimov.features.main.model.element.DayUI
import dev.eakarimov.features.main.model.element.HourUI
import dev.eakarimov.features.main.model.element.LocationUI
import dev.eakarimov.features.main.model.element.common.ConditionUI

internal fun Location.toLocationUI(): LocationUI {
    return LocationUI(
        name = name,
        dateTime = dateTime,
    )
}

internal fun Current.toCurrentUI(): CurrentUI {
    return CurrentUI(
        tempC = tempC,
        condition = condition.toConditionUI(),
        windKph = windKph,
        pressureMb = pressureMb,
        precipMm = precipMm,
        humidity = humidity,
    )
}

internal fun Day.toDayUI(): DayUI {
    return DayUI(
        date = date,
        maxTempC = maxTempC,
        minTempC = minTempC,
        chanceOfRain = chanceOfRain,
        condition = condition.toConditionUI(),
        hours = hours.map(Hour::toHourUI),
    )
}

internal fun Hour.toHourUI(): HourUI {
    return HourUI(
        dateTime = dateTime,
        tempC = tempC,
        condition = condition.toConditionUI(),
        chanceOfRain = chanceOfRain,
    )
}

internal fun Condition.toConditionUI(): ConditionUI {
    return ConditionUI(
        description = description,
        iconUrl = iconUrl,
    )
}

internal fun RequestResult<ForecastUI>.toState(): State {
    return when (this) {
        is RequestResult.InProgress -> State.Loading
        is RequestResult.Success -> State.Success(data)
        is RequestResult.Error -> State.Error
    }
}
