package com.example.weather_app.di

import com.example.weather_app.ui.home.HomeActivityContract
import com.example.weather_app.ui.home.HomeActivityPresenter
import com.example.weather_app.ui.home.home_stories.HomeStoriesFragmentContract
import com.example.weather_app.ui.home.weather_details.HomeStoriesFragmentPresenter
import com.example.weather_app.ui.home.weather_details.WeatherDetailsFragment
import com.example.weather_app.ui.home.weather_details.WeatherDetailsFragmentContract
import com.example.weather_app.ui.home.weather_details.WeatherDetailsFragmentPresenter
import com.example.weather_app.ui.main.MainActivityContract
import com.example.weather_app.ui.main.MainActivityPresenter
import org.koin.dsl.module

val appModule = module {

    single<MainActivityContract.Presenter> {
        MainActivityPresenter(get())
    }
    single<HomeActivityContract.Presenter> {
        HomeActivityPresenter(get())
    }

    single<HomeStoriesFragmentContract.Presenter> {
        HomeStoriesFragmentPresenter(get())
    }

    single<WeatherDetailsFragmentContract.Presenter> {
        WeatherDetailsFragmentPresenter(get())
    }
}