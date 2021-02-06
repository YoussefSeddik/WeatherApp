package com.example.weather_app.data.db.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather_app.data.db.data_base.converters.UserEntityConverter
import com.example.weather_app.data.db.enteties.WeatherEntity

@Database(entities = [WeatherEntity::class], version = AppDatabase.VERSION)
@TypeConverters(UserEntityConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao

    companion object {
        const val VERSION = 2
    }
}