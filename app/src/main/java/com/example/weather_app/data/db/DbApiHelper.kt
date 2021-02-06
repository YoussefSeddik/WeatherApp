package com.example.weather_app.data.db

import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import io.reactivex.Observable

interface DbApiHelper {
    fun getWeatherStories(): Observable<List<WeatherEntity?>?>?
    fun insertWeatherDetails(saveWeatherStoryInput: SaveWeatherStoryInput): Long?
    fun deleteStory(storyId: String): Int

}