package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<Weather> = flow {

        val weather = weatherRepository.getWeather()
        emit(Weather(
            current = weather.current,
            byHours = weather.byHours
                .filter { it.timestamp >= weather.current.timestamp }
                .take(4)
        ))
    }
}