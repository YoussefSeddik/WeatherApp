package com.example.weather_app.domain.mapper

import com.example.weather_app.data.db.enteties.WeatherEntity
import com.example.weather_app.data.remote.models.WeatherStatusResponse
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import com.example.weather_app.domain.models.WeatherStoryModel
import com.example.weather_app.domain.models.WeatherModel
import com.example.weather_app.domain.models.WindDirectionsEnum
import com.example.weather_app.utils.Utils.FULL_DATE_FORMAT
import com.example.weather_app.utils.Utils.getImageIcon
import com.example.weather_app.utils.Utils.getRandomString
import com.example.weather_app.utils.Utils.longToString

fun WeatherStatusResponse.convertToWeatherDetailsModel(): WeatherModel {
    val details = weather.getOrNull(0)

    return WeatherModel(
        tempIcon = details?.main?.getImageIcon(),
        tempDescription = details?.description?.let {
            it.first().toUpperCase() + it.substring(
                1,
                it.length
            )
        },
        temp = main.temp,
        maxTemp = main.temp_max,
        minTemp = main.temp_min,
        feelsLike = main.feels_like,
        pressure = main.pressure,
        humidity = main.humidity,
        windSpeed = wind.speed,
        windDirection = wind.deg.convertToWindDirectionEnum(),
        windVisibility = visibility,
        updatedAt = dt.toLong(),
        countryCode = sys.country,
        cityName = name
    )
}

private fun Int.convertToWindDirectionEnum(): WindDirectionsEnum {
    return when (this) {
        0 -> WindDirectionsEnum.E
        90 -> WindDirectionsEnum.N
        180 -> WindDirectionsEnum.W
        270 -> WindDirectionsEnum.S
        in 0..90 -> WindDirectionsEnum.NE
        in 90..180 -> WindDirectionsEnum.NW
        in 180..270 -> WindDirectionsEnum.SW
        else -> WindDirectionsEnum.SE
    }
}

fun SaveWeatherStoryInput.convertToWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        storyId = getRandomString(6),
        thumbnailPath = thumbnailPath,
        temp = temp.toString(),
        tempDescription = tempDescription,
        updatedAt = longToString(updatedAt, FULL_DATE_FORMAT),
        location = "$countryName, $cityName"
    )
}

fun WeatherEntity.convertToWeatherStoryModel(): WeatherStoryModel {
    return WeatherStoryModel(
        storyId = storyId,
        thumbnailPath = thumbnailPath,
        temp = temp,
        tempDescription = tempDescription,
        location = location,
        updatedAt = updatedAt
    )
}

fun WeatherModel.convertToSaveWeatherInput(thumbnailPath: String): SaveWeatherStoryInput {
    return SaveWeatherStoryInput(
        thumbnailPath = thumbnailPath,
        temp = temp ?: 0.0,
        tempDescription = tempDescription ?: "",
        updatedAt = updatedAt ?: 0L,
        countryName = countryCode ?: "",
        cityName = cityName ?: ""
    )
}

