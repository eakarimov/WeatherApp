package dev.eakarimov.weatherapp

import dev.eakarimov.weatherapi.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApi(
            baseUrl = BuildConfig.WEATHER_API_BASE_URL,
            apiKey = BuildConfig.WEATHER_API_KEY,
        )
    }
}