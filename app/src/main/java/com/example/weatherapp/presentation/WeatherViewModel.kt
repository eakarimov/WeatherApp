package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.error.AppException
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> = _state

    init {
        getWeather()
    }

    private fun getWeather() {
        getWeatherUseCase().onStart {
            _state.value = WeatherState(isLoading = true)
        }.onEach { weather ->
            _state.value = WeatherState(weather = weather)
        }.catch { e ->
            val appError = e as? AppException ?: AppException.Unknown()

            _state.value = WeatherState(
                error = when (appError) {
                    is AppException.Network -> "Проверьте подключение к Интернету."
                    is AppException.Server -> "Произошла ошибка на сервере."
                    is AppException.Unknown -> "Произошла непредвиденная ошибка."
                }
            )
        }.launchIn(viewModelScope)
    }
}