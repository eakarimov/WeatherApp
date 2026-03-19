package com.example.weatherapp.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil3.load
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.RetrofitInstance
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import com.example.weatherapp.domain.model.Weather
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.collections.forEachIndexed

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repository = WeatherRepositoryImpl(RetrofitInstance.api)
    private val getWeatherUseCase = GetWeatherUseCase(repository)
    private val viewModel: WeatherViewModel by viewModels { WeatherViewModelFactory(getWeatherUseCase) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.state.collect {state ->
                state.weather?.let {
                    setupWeather(state.weather)
                    binding.progressBar.isVisible = false
                    binding.mainLayout.isVisible = true
                }
                if (state.isLoading) {
                    binding.progressBar.isVisible = true
                    binding.mainLayout.isVisible = false
                }
                if (state.error.isNotEmpty()) {
                    binding.progressBar.isVisible = false
                    binding.tvError.text = state.error
                    binding.tvError.isVisible = true
                }
            }
        }
    }

    private fun setupWeather(weather: Weather) {
        with(binding) {
            ivCurCond.load("https://${weather.current.iconUrl}")
            tvCurTemp.text = getString(R.string.cur_temp,weather.current.temp)
            tvCurCond.text = weather.current.condition
        }

        with(binding) {
            tvMaxTemp.text = getString(R.string.max_temp, weather.current.tempMax)
            tvMinTemp.text = getString(R.string.min_temp, weather.current.tempMin)
            tvCurDate.text = SimpleDateFormat("LLL, d", Locale.getDefault()).format(Date(weather.current.timestamp * 1000)).capitalize()
        }

        val tvTempList = with(binding) {listOf(tvTemp1, tvTemp2, tvTemp3, tvTemp4)}
        val ivCondList = with(binding) {listOf(ivCond1, ivCond2, ivCond3, ivCond4)}
        val tvHourList = with(binding) {listOf(tvHour1, tvHour2, tvHour3, tvHour4)}

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        weather.byHours.forEachIndexed { index, value ->
            tvTempList[index].text = getString(R.string.hour_temp, value.temp)
            ivCondList[index].load("https://${value.iconIrl}")
            tvHourList[index].text = sdf.format(Date(value.timestamp * 1000))
        }
    }
}