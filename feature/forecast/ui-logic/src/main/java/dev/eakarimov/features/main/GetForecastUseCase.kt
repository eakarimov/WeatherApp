package dev.eakarimov.features.main

import dev.eakarimov.data.ForecastRepository
import dev.eakarimov.data.RequestResult
import dev.eakarimov.data.map
import dev.eakarimov.data.model.forecast.Day
import dev.eakarimov.features.main.model.ForecastUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    operator fun invoke(query: String): Flow<RequestResult<ForecastUI>> {
        return repository.getForecast(query).map { requestResult ->
            requestResult.map { forecast ->
                ForecastUI(
                    location = forecast.location.toLocationUI(),
                    current = forecast.current.toCurrentUI(),
                    days = forecast.days.map(Day::toDayUI),
                )
            }
        }
    }
}
