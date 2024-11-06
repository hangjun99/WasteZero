package kr.ac.konkuk.wastezero.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kr.ac.konkuk.wastezero.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}