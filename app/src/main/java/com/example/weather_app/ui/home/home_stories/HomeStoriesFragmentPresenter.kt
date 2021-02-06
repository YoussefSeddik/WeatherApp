package com.example.weather_app.ui.home.weather_details

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.base.DataResult
import com.example.weather_app.domain.interactors.AppInteractor
import com.example.weather_app.ui.home.home_stories.HomeStoriesFragmentContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeStoriesFragmentPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<HomeStoriesFragmentContract.View>(),
    HomeStoriesFragmentContract.Presenter {
    override fun loadSavedWeatherStories() {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.getSavedWeatherStories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onLoadWeatherStoriesSuccess(it.dataResponse)
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