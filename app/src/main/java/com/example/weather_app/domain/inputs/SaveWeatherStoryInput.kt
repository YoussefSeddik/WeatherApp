package com.example.weather_app.domain.inputs

data class SaveWeatherStoryInput(
    val storyId:String,
    val thumbnailPath: String,
    val temp: Int,
    val tempDescription: String,
    val updatedAt: Long,
    val countryName: String,
    val cityName: String
)
