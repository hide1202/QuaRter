package io.viewpoint.quarter

import android.app.Application
import timber.log.Timber

class QuarterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}