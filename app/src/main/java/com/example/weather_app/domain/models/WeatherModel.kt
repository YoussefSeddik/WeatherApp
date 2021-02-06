package com.example.weather_app.domain.models

data class WeatherModel(
    val tempIcon: Int? = null,
    val tempDescription: String? = null,
    val temp: Int? = null,
    val maxTemp: Int? = null,
    val minTemp: Int? = null,
    val feelsLike: Int? = null,
    val pressure: Int? = null,
    val humidity: Int? = null,
    val countryCode: String? = null,
    val cityName: String? = null,
    val windSpeed: Double? = null,
    val windDirection: WindDirectionsEnum? = null,
    val windVisibility: Int? = null,
    val updatedAt: Long? = null
)

enum class WindDirectionsEnum {
    E, N, W, S, NE, NW, SE, SW
}
