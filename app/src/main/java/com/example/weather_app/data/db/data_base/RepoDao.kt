package com.example.weather_app.data.db.data_base

import androidx.room.*
import com.example.weather_app.data.db.enteties.WeatherEntity
import io.reactivex.Observable

@Dao
interface RepoDao {
    @Query("SELECT * FROM weather")
    fun getWeatherDetailsList(): Observable<List<WeatherEntity?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherDetails(weatherEntity: WeatherEntity): Long?

    @Query("Delete FROM weather where storyId = :storyId")
    fun deleteStory(storyId: String): Int

}