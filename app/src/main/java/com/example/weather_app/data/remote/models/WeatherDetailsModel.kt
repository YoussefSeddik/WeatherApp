package com.example.weather_app.data.remote.models

data class WeatherDetailsModel(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)