package dev.eakarimov.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.eakarimov.data.RequestResult
import dev.eakarimov.features.main.model.ForecastUI
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    getForecastUseCase: GetForecastUseCase,
): ViewModel() {

    val state: StateFlow<State> = getForecastUseCase(query = "Набережные Челны")
        .map(RequestResult<ForecastUI>::toState)
        .stateIn(viewModelScope, SharingStarted.Lazily, State.None)
}