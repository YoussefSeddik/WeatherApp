package com.example.weather_app.data.db

import com.example.weather_app.data.db.data_base.RepoDao
import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import com.example.weather_app.domain.mapper.convertToWeatherEntity
import io.reactivex.Observable

class DbApiHelperImpl(private val repoDao: RepoDao) : DbApiHelper {

    override fun getWeatherStories(): Observable<List<WeatherEntity?>?>? {
        return repoDao.getWeatherDetailsList()
    }

    override fun insertWeatherDetails(saveWeatherStoryInput: SaveWeatherStoryInput): Long? {
        return repoDao.insertWeatherDetails(saveWeatherStoryInput.convertToWeatherEntity())
    }

    override fun deleteStory(storyId: String): Int{
        return repoDao.deleteStory(storyId)
    }
}