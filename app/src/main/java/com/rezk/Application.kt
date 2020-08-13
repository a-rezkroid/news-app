package com.rezk

import android.app.Application
import com.rezk.newsfeedsapp.di.networkModule
import com.rezk.newsfeedsapp.di.repoModule
import com.rezk.newsfeedsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(viewModelModule, repoModule, networkModule)
        }
    }
}