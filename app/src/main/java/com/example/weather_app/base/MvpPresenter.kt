package com.example.weather_app.base

import androidx.lifecycle.Lifecycle

interface MvpPresenter<V> {
    fun attachView(view: V,lifecycle: Lifecycle?)
    fun deAttachView()
}