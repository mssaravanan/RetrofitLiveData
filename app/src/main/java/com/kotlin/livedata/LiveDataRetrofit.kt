package com.kotlin.livedata

import android.app.Application
import android.content.Context

class LiveDataRetrofit : Application() {

    companion object {
        lateinit var app: Application
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        appContext = this
    }
}