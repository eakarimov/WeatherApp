package dev.eakarimov.features.main

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
import dagger.hilt.android.AndroidEntryPoint
import dev.eakarimov.features.main.model.ForecastUI
import dev.eakarimov.features.main.ui.R
import dev.eakarimov.features.main.ui.databinding.FragmentForecastBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ForecastViewModel by viewModels()
    private lateinit var hourUIAdapter: HourUIAdapter
    private lateinit var dayUIAdapter: DayUIAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclers()
        collectState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclers() {
        binding.rvHours.apply {
            hourUIAdapter = HourUIAdapter()
            adapter = hourUIAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        binding.rvDays.apply {
            dayUIAdapter = DayUIAdapter()
            adapter = dayUIAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state -> showScreen(state) }
            }
        }
    }

    private fun showScreen(state: State) {
        return when (state) {
            is State.None -> Unit
            is State.Loading -> showLoadingScreen()
            is State.Success -> showSuccessScreen(state.forecast)
            is State.Error -> showErrorScreen()
        }
    }

    private fun showSuccessScreen(forecast: ForecastUI) {
        hourUIAdapter.submitList(forecast.days.first().hours)
        dayUIAdapter.submitList(forecast.days)

        val daysCount = forecast.days.size
        val dayOfMonth = forecast.location.dateTime.dayOfMonthString
        val dayOfWeek = forecast.location.dateTime.dayOfWeekString

        with(binding) {
            tvCity.text = forecast.location.name
            tvDayOfWeek.text = dayOfWeek
            tvDayOfMonth.text = dayOfMonth
            tvForecastDays.text = resources.getQuantityString(
                R.plurals.days, daysCount, daysCount
            )
            ivCurCond.load(forecast.current.condition.iconUrl)
            tvCurTemp.text = forecast.current.tempC.toString()
            tvCurCond.text = forecast.current.condition.description
            tvWindSpeed.text = getString(R.string.wind_value, forecast.current.windKph)
            tvPrecip.text = getString(R.string.precip_value, forecast.current.precipMm)
            tvPressure.text = getString(R.string.pressure_value, forecast.current.pressureMb)
            tvHumidity.text = getString(R.string.humidity_value, forecast.current.humidity)
            tvHourlyTitle.text = getString(R.string.hours, dayOfWeek, dayOfMonth)
        }

        with(binding) {
            loadingIndicator.isVisible = false
            frForecast.isVisible = true
            tvError.isVisible = false
        }
    }

    private fun showLoadingScreen() {
        with(binding) {
            loadingIndicator.isVisible = true
            frForecast.isVisible = false
            tvError.isVisible = false
        }
    }

    private fun showErrorScreen() {
        with(binding) {
            loadingIndicator.isVisible = false
            frForecast.isVisible = false
            tvError.isVisible = true
        }
    }
}