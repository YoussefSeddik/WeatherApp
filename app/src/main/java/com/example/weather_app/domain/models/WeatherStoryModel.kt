package com.example.weather_app.domain.models


data class WeatherStoryModel(
    val storyId:String,
    val thumbnailPath: String,
    val temp: String,
    val tempDescription: String,
    val location: String,
    val updatedAt: String
)