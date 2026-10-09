package dev.eakarimov.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import dev.eakarimov.features.main.model.element.HourUI
import dev.eakarimov.features.main.ui.R
import dev.eakarimov.features.main.ui.databinding.ItemHourBinding

internal class HourUIAdapter : ListAdapter<HourUI, HourUIAdapter.HourUIViewHolder>(DiffCallback) {

    internal class HourUIViewHolder(
        val binding: ItemHourBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourUIViewHolder {
        return HourUIViewHolder(
            ItemHourBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HourUIViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            tvHour.text = item.dateTime.hourString
            ivIcon.load(item.condition.iconUrl)
            with(root.context) {
                tvTemp.text = getString(R.string.temp_value, item.tempC)
                tvChanceOfRain.text = getString(R.string.rain_value, item.chanceOfRain)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HourUI>() {

        override fun areItemsTheSame(oldItem: HourUI, newItem: HourUI): Boolean {
            return oldItem.dateTime == newItem.dateTime
        }

        override fun areContentsTheSame(oldItem: HourUI, newItem: HourUI): Boolean {
            return oldItem == newItem
        }
    }
}
