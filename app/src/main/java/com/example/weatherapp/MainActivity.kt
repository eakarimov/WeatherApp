package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil3.load
import coil3.network.HttpException
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import okio.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

        lifecycleScope.launch {
            with(binding) {
                progressBar.isVisible = true
                mainLayout.isVisible = false
            }

            val response = try {
                RetrofitInstance.api.getWeather()
            } catch (e: IOException) {
                Log.e(TAG, "IOException: no internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException: unexpected response")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) setupData(response.body()!!)

            with(binding) {
                progressBar.isVisible = false
                mainLayout.isVisible = true
            }
        }
    }

    private fun setupData(data: WeatherResponse) {
        val date: Date = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            .parse(data.location.localtime)!!
        val currentHour = data.location.localtime.substring(11,13).toInt()

        with(binding) {
            ivCurCond.load("https://${data.current.condition.icon}")
            tvCurTemp.text = getString(R.string.cur_temp,data.current.temp_c)
            tvCurCond.text = data.current.condition.text
        }

        val forecastToday = data.forecast.forecastday[0]

        with(binding) {
            tvMaxTemp.text = getString(R.string.max_temp, forecastToday.day.maxtemp_c)
            tvMinTemp.text = getString(R.string.min_temp, forecastToday.day.mintemp_c)
            tvCurDate.text = SimpleDateFormat("MMM, d", Locale.US).format(date)
        }

        val weatherHourlyBlock = with(binding) {
            listOf(
                HourlyForecast(tvTemp1, ivCond1, tvHour1),
                HourlyForecast(tvTemp2, ivCond2, tvHour2),
                HourlyForecast(tvTemp3, ivCond3, tvHour3),
                HourlyForecast(tvTemp4, ivCond4, tvHour4),
            )
        }
        weatherHourlyBlock.forEachIndexed { index, view ->
            val hourIndex = currentHour + index
            val realHour = hourIndex % 24

            val hourData = data.forecast.forecastday[hourIndex / 24].hour[realHour]

            with(view) {
                temp.text = getString(R.string.hour_temp, hourData.temp_c)
                cond.load("https://${hourData.condition.icon}")
                hour.text = getString(R.string.hour_time, realHour)
            }
        }
    }

    data class HourlyForecast(
        val temp: TextView,
        val cond: ImageView,
        val hour: TextView,
    )

    companion object {
        const val TAG = "exception"
    }
}