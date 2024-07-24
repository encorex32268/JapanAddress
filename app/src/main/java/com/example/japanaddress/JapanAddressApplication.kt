package com.example.japanaddress

import android.app.Application
import com.example.japanaddress.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JapanAddressApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@JapanAddressApplication)
            modules(
                appModule
            )
        }
    }
}