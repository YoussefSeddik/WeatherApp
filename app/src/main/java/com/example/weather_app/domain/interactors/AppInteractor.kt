package com.example.weather_app.domain.interactors

import com.example.weather_app.base.DataResult
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import com.example.weather_app.domain.models.WeatherStoryModel
import com.example.weather_app.domain.models.WeatherModel
import io.reactivex.Observable

interface AppInteractor {
    fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput): Observable<DataResult<WeatherModel>>
    fun saveWeatherStory(saveWeatherStoryInput: SaveWeatherStoryInput): Observable<DataResult<Unit>>
    fun getSavedWeatherStories(): Observable<DataResult<MutableList<WeatherStoryModel>>>
    fun deleteStory(storyId:String): Observable<DataResult<Unit>>


}