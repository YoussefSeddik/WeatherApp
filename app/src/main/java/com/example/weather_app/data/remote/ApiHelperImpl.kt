package com.example.weather_app.data.remote

import com.example.weather_app.data.remote.models.WeatherStatusResponse
import io.reactivex.Observable


class ApiHelperImpl(private val retrofitApiService: RetrofitApiService) : ApiHelper {

    override fun getWeatherDetails(lat: Double, lon: Double): Observable<WeatherStatusResponse?> {
        return retrofitApiService.getWeatherDetails(lat, lon)
    }

}