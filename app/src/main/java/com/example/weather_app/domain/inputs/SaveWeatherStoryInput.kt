package com.example.weather_app.domain.inputs

data class SaveWeatherStoryInput(
    val thumbnailPath: String,
    val temp: Double,
    val tempDescription: String,
    val updatedAt: Long,
    val countryName: String,
    val cityName: String
)
