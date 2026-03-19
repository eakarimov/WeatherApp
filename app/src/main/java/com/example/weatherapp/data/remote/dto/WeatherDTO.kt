package com.example.weatherapp.data.remote.dto


import com.example.weatherapp.domain.model.WeatherCurrent
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.model.WeatherByHour
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)

fun WeatherDTO.toWeather(): Weather {
    return Weather(
        current = WeatherCurrent(
            timestamp = location.localtimeEpoch,
            temp = current.tempC,
            condition = current.condition.text,
            iconUrl = current.condition.icon,
            tempMax = forecast.forecastday[0].day.maxtempC,
            tempMin = forecast.forecastday[0].day.mintempC,
        ),
        byHours = forecast.forecastday.flatMap { forecastday ->
            forecastday.hour.map {hour ->
                WeatherByHour(
                    timestamp = hour.timeEpoch,
                    temp = hour.tempC,
                    iconIrl = hour.condition.icon,
                )
            }
        }
    )
}