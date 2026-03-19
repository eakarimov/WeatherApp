package com.example.weatherapp.domain.usecase

import com.example.weatherapp.common.Resource
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<Resource<Weather>> = flow {

        // TODO: убрать обработку исключений полностью в data-слой

        try {
            emit(Resource.Loading<Weather>())
            var weather = weatherRepository.getWeather()
            weather = Weather(
                current = weather.current,
                byHours = weather.byHours
                    .filter { it.timestamp >= weather.current.timestamp }
                    .take(4)
            )
            emit(Resource.Success<Weather>(weather))
        } catch (e: IOException) {
            emit(Resource.Error<Weather>("Сервер не отвечает. Проверьте подключение к Интернету."))
        } catch (e: HttpException) {
            emit(Resource.Error<Weather>("Ошибка сервера. Попробуйте зайти позже."))
        }
    }
}