package com.togedo.app

import android.app.Application
import com.togedo.app.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@AndroidApp)
            androidLogger()
        }
    }
}