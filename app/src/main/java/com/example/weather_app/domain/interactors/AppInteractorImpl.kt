package com.example.weather_app.domain.interactors

import com.example.weather_app.R
import com.example.weather_app.base.DataResult
import com.example.weather_app.data.db.DbApiHelper
import com.example.weather_app.data.pref.PrefHelper
import com.example.weather_app.data.remote.ApiHelper
import com.example.weather_app.data.resource_helper.ResourceHelper
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import com.example.weather_app.domain.mapper.convertToWeatherStoryModel
import com.example.weather_app.domain.mapper.convertToWeatherDetailsModel
import com.example.weather_app.domain.models.WeatherStoryModel
import com.example.weather_app.domain.models.WeatherModel
import com.example.weather_app.utils.Delay
import io.reactivex.Observable

class AppInteractorImpl(
    private val apiHelper: ApiHelper,
    private val prefHelper: PrefHelper,
    private val dbApiHelper: DbApiHelper,
    private val resourceHelper: ResourceHelper
) :
    AppInteractor {
    override fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput): Observable<DataResult<WeatherModel>> {
        return apiHelper.getWeatherDetails(getWeatherDetailsInput.lat, getWeatherDetailsInput.lon)
            .map {
                if (it.cod == 200) {
                    val weatherModel = it.convertToWeatherDetailsModel()
                    DataResult.Success<WeatherModel>(weatherModel)
                } else {
                    DataResult.Failure<WeatherModel>(resourceHelper.getStringResource(R.string.network_error))
                }
            }
    }

    override fun saveWeatherStory(saveWeatherStoryInput: SaveWeatherStoryInput): Observable<DataResult<Unit>> {
        val result = dbApiHelper.insertWeatherDetails(saveWeatherStoryInput)
        return result?.let {
            Observable.just(DataResult.Success<Unit>(Unit))
        }
            ?: Observable.just(DataResult.Failure<Unit>(resourceHelper.getStringResource(R.string.error_saving_weather_story)))
    }

    override fun getSavedWeatherStories(): Observable<DataResult<MutableList<WeatherStoryModel>>> {
        val result = dbApiHelper.getWeatherStories()
        return result?.flatMap { it ->
            val weatherStoriesList = it.mapNotNull { weatherEntity ->
                weatherEntity?.convertToWeatherStoryModel()
            }.toMutableList()
            Observable.just(
                DataResult.Success<MutableList<WeatherStoryModel>>(weatherStoriesList)
            )
        } ?: Observable.just(
            DataResult.Failure<MutableList<WeatherStoryModel>>(
                resourceHelper.getStringResource(
                    R.string.error_getting_saved_weather_stories
                )
            )
        )
    }

    override fun deleteStory(storyId: String): Observable<DataResult<Unit>> {
        val result = dbApiHelper.deleteStory(storyId)
        return result?.let {
            Observable.just(DataResult.Success<Unit>(Unit))
        }
            ?: Observable.just(DataResult.Failure<Unit>(resourceHelper.getStringResource(R.string.error_deleting_weather_story)))
    }
}