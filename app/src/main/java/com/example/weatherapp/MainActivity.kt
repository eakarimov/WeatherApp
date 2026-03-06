package com.example.weatherapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil3.load
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
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

                val response = RetrofitInstance.api.getWeather()

                with(response.body()!!) {
                    val date: Date = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        .parse(location.localtime)!!
                    val hour = location.localtime.substring(11,13).toInt()

                    ivCurCond.load("https://${current.condition.icon}")
                    tvCurTemp.text = format(R.string.cur_temp,current.temp_c)
                    tvCurCond.text = current.condition.text

                    with(forecast) {
                        tvMaxTemp.text = format(R.string.max_temp, forecastday[0].day.maxtemp_c)
                        tvMinTemp.text = format(R.string.min_temp, forecastday[0].day.mintemp_c)

                        tvCurDate.text = SimpleDateFormat("MMM, d", Locale.US).format(date)

                        val tvTemps = listOf(tvTemp1, tvTemp2, tvTemp3, tvTemp4)
                        val ivConds = listOf(ivCond1, ivCond2, ivCond3, ivCond4)
                        val tvHours = listOf(tvHour1, tvHour2, tvHour3, tvHour4)
                        repeat(4) {index ->
                            val hourIndex = hour + index

                            if (hourIndex < 24) {
                                tvTemps[index].text = format(R.string.hour_temp, forecastday[0].hour[hourIndex].temp_c)
                                ivConds[index].load("https://${forecastday[0].hour[hourIndex].condition.icon}")
                                tvHours[index].text = format(R.string.hour_time, hourIndex)
                            } else {
                                tvTemps[index].text = format(R.string.hour_temp, forecastday[1].hour[hourIndex - 24].temp_c)
                                ivConds[index].load("https://${forecastday[1].hour[hourIndex - 24].condition.icon}")
                                tvHours[index].text = format(R.string.hour_time, hourIndex - 24)
                            }
                        }
                    }
                }

                progressBar.isVisible = false
                mainLayout.isVisible = true
            }
        }
    }

    fun format(@StringRes id: Int, arg: Any) = String.format(
        Locale.US,
        resources.getString(id),
        arg
    )
}