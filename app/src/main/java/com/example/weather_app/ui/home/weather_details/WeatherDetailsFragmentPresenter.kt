package com.example.weather_app.ui.home.weather_details

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.base.DataResult
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import com.example.weather_app.domain.interactors.AppInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class WeatherDetailsFragmentPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<WeatherDetailsFragmentContract.View>(),
    WeatherDetailsFragmentContract.Presenter {
    override fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput) {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.getWeatherDetails(getWeatherDetailsInput).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onGetWeatherDetailsSuccess(it.dataResponse)
                            }
                            is DataResult.Failure -> {
                                view?.showMessage(it.error)
                            }
                        }
                    },
                    onError = {
                        view?.hideLoading()
                        view?.onNetworkError()
                    }
                )
        )
    }

    override fun saveWeatherStory(saveWeatherStoryInput: SaveWeatherStoryInput) {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.saveWeatherStory(saveWeatherStoryInput).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onSaveWeatherStorySuccess(saveWeatherStoryInput)
                            }
                            is DataResult.Failure -> {
                                view?.showMessage(it.error)
                            }
                        }
                    },
                    onError = {
                        view?.hideLoading()
                        view?.onNetworkError()
                    }
                )
        )
    }
    override fun deleteWeatherStory(storyId: String) {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.deleteStory(storyId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onDeleteWeatherStorySuccess(storyId)
                            }
                            is DataResult.Failure -> {
                                view?.showMessage(it.error)
                            }
                        }
                    },
                    onError = {
                        view?.hideLoading()
                        view?.onNetworkError()
                    }
                )
        )
    }
}