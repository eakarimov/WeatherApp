package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<Weather> = flow {

        val weather = weatherRepository.getWeather()
        emit(weather.copy(
            hourly = weather.hourly
                .drop(weather.location.currentHour)
                .take(24 )
                .mapIndexed { index, hourlyWeather ->
                    if (index == 0) {
                        hourlyWeather.copy(hourText = "Now")
                    } else {
                        hourlyWeather
                    }
                }
            )
        )
    }
}