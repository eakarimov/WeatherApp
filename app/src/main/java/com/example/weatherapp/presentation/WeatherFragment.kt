package com.example.weatherapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.domain.error.AppException
import com.example.weatherapp.domain.model.Weather
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var hourlyAdapter: HourlyWeatherAdapter
    private lateinit var dailyAdapter: DailyWeatherAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHourly.apply {
            hourlyAdapter = HourlyWeatherAdapter()
            adapter = hourlyAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
        }
        binding.rvDaily.apply {
            dailyAdapter = DailyWeatherAdapter()
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {state ->
                    with (binding) {
                        when {
                            state.isLoading -> {
                                loadingIndicator.isVisible = true
                                weatherFragment.isVisible = false
                                tvError.isVisible = false
                            }
                            state.error != null -> {
                                loadingIndicator.isVisible = false
                                weatherFragment.isVisible = false
                                tvError.text = mapError(state.error)
                                tvError.isVisible = true
                            }
                            state.weather != null -> {
                                setupWeather(state.weather)
                                loadingIndicator.isVisible = false
                                weatherFragment.isVisible = true
                                tvError.isVisible = false
                            }
                        }
                    }
                }
            }
        }
    }

    private fun mapError(error: AppException): String = when (error) {

        is AppException.Network -> getString(R.string.error_network)
        is AppException.Server -> getString(R.string.error_server)
        is AppException.Unknown -> getString(R.string.error_unknown)
    }

    private fun setupWeather(weather: Weather) {

        hourlyAdapter.hourlyWeatherList = weather.hourly
        dailyAdapter.dailys = weather.daily

        with(binding) {
            tvCity.text = weather.location.name
            tvDayOfWeek.text = weather.location.dayOfWeek
            tvDate.text = weather.location.monthAndDay

            ivCurCond.load(weather.current.iconUrl)
            tvCurTemp.text = weather.current.temperature.toString()
            tvCurCond.text = weather.current.condition
            tvWindSpeed.text = getString(R.string.weather_wind_speed, weather.current.windSpeed)
            tvPrecip.text = getString(R.string.weather_precipitation_amount, weather.current.precipitation)
            tvPressure.text = getString(R.string.weather_pressure_value, weather.current.pressure)
            tvHumidity.text = getString(R.string.weather_humidity_value, weather.current.humidity)
            tvHourlyTitle.text = getString(R.string.weather_hourly_title, weather.location.dayOfWeek, weather.location.monthAndDay)
        }
    }
}