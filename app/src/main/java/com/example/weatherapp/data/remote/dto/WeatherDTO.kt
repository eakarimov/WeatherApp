package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.CurrentWeather
import com.example.weatherapp.domain.model.DailyWeather
import com.example.weatherapp.domain.model.HourlyWeather
import com.example.weatherapp.domain.model.Location
import com.example.weatherapp.domain.model.Weather
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

data class WeatherDTO(
    @SerializedName("location")
    val location: LocationDTO,
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
)

fun WeatherDTO.toWeather(): Weather {
    return Weather(
        location = Location(
            name = location.name,
            currentHour = location.localtime.extractHour(),
            dayOfWeek = LocalDateTime.parse(
                location.localtime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US)
            ).format(DateTimeFormatter.ofPattern("EEEE", Locale.US)),
            monthAndDay = LocalDateTime.parse(
                location.localtime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US)
            ).format(DateTimeFormatter.ofPattern("MMM d", Locale.US))
        ),
        current = CurrentWeather(
            temperature = current.tempC.roundToInt(),
            condition = current.condition.text,
            iconUrl = current.condition.icon.toHttps(),
            windSpeed = current.windKph,
            precipitation = current.precipMm,
            pressure = current.pressureMb.roundToInt(),
            humidity = current.humidity
        ),
        hourly = forecast.forecastday.flatMap { forecastDay ->
            forecastDay.hour.map {hour ->
                HourlyWeather(
                    hour = hour.time.extractHour(),
                    hourText = hour.time.takeLast(5),
                    temperature = hour.tempC.roundToInt(),
                    iconUrl = hour.condition.icon.toHttps(),
                    chanceOfRain = hour.chanceOfRain,
                )
            }
        },
        daily = forecast.forecastday.map { forecastDay ->
            DailyWeather(
                timestamp = forecastDay.dateEpoch,
                dayOfWeek = LocalDate.parse(forecastDay.date).format(
                    DateTimeFormatter.ofPattern("EEE", Locale.US)
                ),
                minTemp = forecastDay.day.mintempC.roundToInt(),
                maxTemp = forecastDay.day.maxtempC.roundToInt(),
                iconUrl = forecastDay.day.condition.icon.toHttps(),
                chanceOfRain = forecastDay.day.dailyChanceOfRain,
            )
        },
    )
}

private fun String.toHttps(): String = if (startsWith("http")) this else "https:${this}"

private fun String.extractHour(): Int =
    this.substringAfter(" ")
        .substringBefore(":")
        .toInt()