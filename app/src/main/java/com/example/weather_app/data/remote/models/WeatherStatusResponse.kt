package com.example.weather_app.data.remote.models

data class WeatherStatusResponse(
    val cod: Int,
    val dt: Int,
    val main: TemperatureModel,
    val weather: List<WeatherDetailsModel>,
    val wind: WindModel,
    val visibility: Int,
    val sys: CountryModel,
    val name: String
)