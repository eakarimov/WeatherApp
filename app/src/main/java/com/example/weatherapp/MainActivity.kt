package com.example.weatherapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

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
            val response = RetrofitInstance.api.getWeather()
            binding.tvCurTemp.text = "${response.body()!!.current.temp_c}°"
            binding.tvCurCond.text = response.body()!!.current.condition.text
            binding.tvMaxTemp.text = "Max: ${response.body()!!.forecast.forecastday[0].day.maxtemp_c}°"
            binding.tvMinTemp.text = "Min: ${response.body()!!.forecast.forecastday[0].day.mintemp_c}°"
        }
    }
}