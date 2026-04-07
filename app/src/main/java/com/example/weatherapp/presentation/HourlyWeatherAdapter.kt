package com.example.weatherapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemHourlyWeatherBinding
import com.example.weatherapp.domain.model.HourlyWeather

class HourlyWeatherAdapter: RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    class HourlyWeatherViewHolder(val binding: ItemHourlyWeatherBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object: DiffUtil.ItemCallback<HourlyWeather>() {

        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean =
            oldItem.hour == newItem.hour

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var hourlyWeatherList: List<HourlyWeather>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount(): Int = hourlyWeatherList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {

        return HourlyWeatherViewHolder(ItemHourlyWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {

        holder.binding.apply {
            val hourlyWeather = hourlyWeatherList[position]

            tvHour.text = hourlyWeather.hourText
            ivIcon.load(hourlyWeather.iconUrl)
            with (holder.itemView.context) {
                tvTemp.text = getString(R.string.weather_temperature, hourlyWeather.temperature)
                tvChanceOfRain.text = getString(R.string.weather_rain_chance, hourlyWeather.chanceOfRain)
            }
        }
    }
}