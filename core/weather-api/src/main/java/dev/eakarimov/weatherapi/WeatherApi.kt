package dev.eakarimov.weatherapi

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dev.eakarimov.weatherapi.model.ForecastDTO
import dev.eakarimov.weatherapi.model.ResponseDTO
import dev.eakarimov.weatherapi.util.WeatherApiKeyInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [API documentation](https://www.weatherapi.com/docs/)
 * */
interface WeatherApi {

    /**
     * [Endpoint details](https://www.weatherapi.com/docs/#apis-forecast)
     * */
    @GET("forecast.json")
    suspend fun forecast(
        @Query("q") query: String? = null,
        @Query("days") days: Int = 3,
    ): Result<ResponseDTO<ForecastDTO>>
}

fun WeatherApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json { ignoreUnknownKeys = true },
): WeatherApi {
    return retrofit(baseUrl, apiKey, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(WeatherApiKeyInterceptor(apiKey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}
