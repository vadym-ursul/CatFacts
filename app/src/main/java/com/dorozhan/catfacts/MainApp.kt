package com.dorozhan.catfacts

import android.app.Application
import com.dorozhan.catfacts.di.ktorModule
import com.dorozhan.catfacts.di.repositoryModule
import com.dorozhan.catfacts.di.retrofitModule
import com.dorozhan.catfacts.di.viewModelModule
import com.google.android.material.color.DynamicColors
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDynamicColors()

        startKoin {
            androidContext(this@MainApp)
            modules(listOf(ktorModule, retrofitModule, repositoryModule, viewModelModule))
        }
    }

    private fun initDynamicColors() {
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}