package com.example.weather_app.utils

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.util.concurrent.TimeUnit

object Delay {
    fun <T> observable(block: (emitter: ObservableEmitter<T>) -> Unit): Observable<T> {
        return Observable.timer(3, TimeUnit.SECONDS).flatMap {
            Observable.create(block)
        }.doOnError {
            Log.e("Elgoe", it.message ?: "")
        }
    }
}