package com.example.weather_app.ui.home.home_stories

import com.example.weather_app.base.MvpPresenter
import com.example.weather_app.base.MvpViewUtils
import com.example.weather_app.domain.models.WeatherStoryModel

interface HomeStoriesFragmentContract {

    interface Presenter : MvpPresenter<View> {
        fun loadSavedWeatherStories()
        fun deleteWeatherStory(storyId:String)
    }

    interface View : MvpViewUtils {
        fun onLoadWeatherStoriesSuccess(weatherStoryDetailsList: MutableList<WeatherStoryModel>)
        fun onDeleteWeatherStorySuccess(storyId:String)

    }



}