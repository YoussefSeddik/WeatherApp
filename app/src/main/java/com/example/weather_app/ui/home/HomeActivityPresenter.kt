package com.example.weather_app.ui.home

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.base.DataResult
import com.example.weather_app.domain.interactors.AppInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeActivityPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<HomeActivityContract.View>(),
    HomeActivityContract.Presenter {

}