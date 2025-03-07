package com.example.weather_app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.weather_app.di.appModule
import com.example.weather_app.di.dataModule
import com.example.weather_app.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            loadKoinModules(
                mutableListOf(
                    dataModule,
                    appModule,
                    domainModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}