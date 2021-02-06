package com.example.weather_app.ui.home.weather_details

import com.example.weather_app.base.MvpPresenter
import com.example.weather_app.base.MvpViewUtils
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import com.example.weather_app.domain.models.WeatherModel

interface WeatherDetailsFragmentContract {

    interface Presenter : MvpPresenter<View> {
        fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput)
        fun saveWeatherStory(saveWeatherStoryInput: SaveWeatherStoryInput)
    }

    interface View : MvpViewUtils {
        fun onGetWeatherDetailsSuccess(weatherModel: WeatherModel)
        fun onSaveWeatherStorySuccess(storyPath:String)
    }


}