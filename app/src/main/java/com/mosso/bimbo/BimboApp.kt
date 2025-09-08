package com.mosso.bimbo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BimboApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}