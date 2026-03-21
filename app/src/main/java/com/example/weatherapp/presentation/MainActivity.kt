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
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.error.AppException
import com.example.weatherapp.domain.model.Weather
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.forEachIndexed

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()


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
                with (binding) {
                    when {
                        state.isLoading -> {
                            progressBar.isVisible = true
                            mainLayout.isVisible = false
                            tvError.isVisible = false
                        }
                        state.error != null -> {
                            progressBar.isVisible = false
                            mainLayout.isVisible = false
                            tvError.text = mapError(state.error)
                            tvError.isVisible = true
                        }
                        state.weather != null -> {
                            setupWeather(state.weather)
                            progressBar.isVisible = false
                            mainLayout.isVisible = true
                            tvError.isVisible = false
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