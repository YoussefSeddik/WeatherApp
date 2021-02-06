package com.example.weather_app.data.remote

import com.example.weather_app.data.remote.models.WeatherStatusResponse
import io.reactivex.Observable

interface ApiHelper {

    fun getWeatherDetails(lat: Double, lon: Double): Observable<WeatherStatusResponse?>
}