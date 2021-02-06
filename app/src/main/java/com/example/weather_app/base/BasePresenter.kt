package com.example.weather_app.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V> : MvpPresenter<V>, LifecycleObserver {
    var view: V? = null
    var compositeDisposable = CompositeDisposable()

    override fun attachView(view: V,lifecycle: Lifecycle?) {
        this.view = view
        lifecycle?.addObserver(this)
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun deAttachView() {
        this.view = null
        compositeDisposable.dispose()
    }


}