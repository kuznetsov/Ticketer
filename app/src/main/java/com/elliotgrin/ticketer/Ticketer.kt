package com.elliotgrin.ticketer

import android.app.Application
import com.elliotgrin.ticketer.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class Ticketer : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initKoin()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@Ticketer)
            fragmentFactory()
            modules(fragmentsModule + viewModelsModule + repositoriesModule + apiModule)
        }
    }

}
