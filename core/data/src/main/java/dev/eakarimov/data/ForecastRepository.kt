package dev.eakarimov.data

import dev.eakarimov.data.model.Forecast
import dev.eakarimov.weatherapi.WeatherApi
import dev.eakarimov.weatherapi.model.ForecastDTO
import dev.eakarimov.weatherapi.model.ForecastDayDTO
import dev.eakarimov.weatherapi.model.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val api: WeatherApi,
) {

    fun getForecast(query: String): Flow<RequestResult<Forecast>> {
        return flow {
            emit(RequestResult.InProgress())

            val result = api.forecast(query)

            if (result.isSuccess) {
                val response: ResponseDTO<ForecastDTO> = result.getOrThrow()
                emit(RequestResult.Success(
                    Forecast(
                        location = response.location.toLocation(),
                        current = response.current.toCurrent(),
                        days = response.forecast.forecastDay.map(ForecastDayDTO::toDay),
                    )
                ))
            } else {
                emit(RequestResult.Error())
            }
        }
    }
}
