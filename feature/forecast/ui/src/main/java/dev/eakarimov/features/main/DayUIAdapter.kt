package dev.eakarimov.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import dev.eakarimov.features.main.model.element.DayUI
import dev.eakarimov.features.main.ui.R
import dev.eakarimov.features.main.ui.databinding.ItemDayBinding

internal class DayUIAdapter : ListAdapter<DayUI, DayUIAdapter.DayUIViewHolder>(DiffCallback) {

    internal class DayUIViewHolder(
        val binding: ItemDayBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayUIViewHolder {
        return DayUIViewHolder(
            ItemDayBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DayUIViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            tvDayOfWeek.text = item.date.dayOfWeekString
            ivIcon.load(item.condition.iconUrl)
            with(root.context) {
                tvChanceOfRain.text = getString(R.string.rain_value, item.chanceOfRain)
                tvTemp.text =
                    getString(R.string.temp_min_max_value, item.minTempC, item.maxTempC)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DayUI>() {

        override fun areItemsTheSame(oldItem: DayUI, newItem: DayUI): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DayUI, newItem: DayUI): Boolean {
            return oldItem == newItem
        }
    }
}
