package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.error.AppException
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(): Weather {

       try {
           return api.getWeather().toWeather()
        } catch (e: IOException) {
            throw AppException.Network()
        } catch (e: HttpException) {
            throw AppException.Server()
        } catch (e: Exception) {
            throw AppException.Unknown()
        }
    }
}