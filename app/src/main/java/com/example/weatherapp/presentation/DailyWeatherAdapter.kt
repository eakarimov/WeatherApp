package com.example.weatherapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemDailyWeatherBinding
import com.example.weatherapp.domain.model.DailyWeather

class DailyWeatherAdapter : RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {

    class DailyWeatherViewHolder(
        val binding: ItemDailyWeatherBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DailyWeather) = with (binding) {
            tvDayOfWeek.text = item.dayOfWeek
            ivIcon.load(item.iconUrl)
            with (binding.root.context) {
                tvChanceOfRain.text = getString(R.string.weather_rain_chance, item.chanceOfRain)
                tvTemp.text = getString(R.string.weather_temperature_min_max, item.minTemp, item.maxTemp)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<DailyWeather>() {

        override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
            oldItem.timestamp == newItem.timestamp

        override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var dailys: List<DailyWeather>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount(): Int = dailys.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {

        return DailyWeatherViewHolder(ItemDailyWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.bind(dailys[position])
    }
}