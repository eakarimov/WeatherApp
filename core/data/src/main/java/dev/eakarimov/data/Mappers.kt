package dev.eakarimov.data

import dev.eakarimov.data.model.forecast.Current
import dev.eakarimov.data.model.forecast.Day
import dev.eakarimov.data.model.forecast.Hour
import dev.eakarimov.data.model.forecast.Location
import dev.eakarimov.data.model.forecast.common.Condition
import dev.eakarimov.weatherapi.model.ConditionDTO
import dev.eakarimov.weatherapi.model.CurrentDTO
import dev.eakarimov.weatherapi.model.ForecastDayDTO
import dev.eakarimov.weatherapi.model.HourDTO
import dev.eakarimov.weatherapi.model.LocationDTO
import kotlin.math.roundToInt

internal fun LocationDTO.toLocation(): Location {
    return Location(
        name = name,
        dateTime = localtime,
    )
}

internal fun CurrentDTO.toCurrent(): Current {
    return Current(
        tempC = tempC.roundToInt(),
        condition = condition.toCondition(),
        windKph = windKph,
        pressureMb = pressureMb.roundToInt(),
        precipMm = precipMm,
        humidity = humidity,
    )
}

internal fun ForecastDayDTO.toDay(): Day {
    return Day(
        date = date,
        maxTempC = day.maxTempC.roundToInt(),
        minTempC = day.minTempC.roundToInt(),
        chanceOfRain = day.dailyChanceOfRain,
        condition = day.condition.toCondition(),
        hours = hour.map(HourDTO::toHour),
    )
}

internal fun HourDTO.toHour(): Hour {
    return Hour(
        dateTime = time,
        tempC = tempC.roundToInt(),
        condition = condition.toCondition(),
        chanceOfRain = chanceOfRain,
    )
}

internal fun ConditionDTO.toCondition(): Condition {
    return Condition(
        description = text,
        iconUrl = icon.asNormalizedUrl,
    )
}

private val String.asNormalizedUrl: String
    get() = when {
        this.startsWith("https://") -> this
        this.startsWith("http://") -> this
        this.startsWith("//") -> "https:$this"
        else -> "https://$this"
    }
