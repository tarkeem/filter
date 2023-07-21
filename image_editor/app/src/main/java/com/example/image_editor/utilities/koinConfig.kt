package com.example.image_editor.utilities

import com.example.image_editor.di.reposatoryModule
import com.example.image_editor.di.viewModelModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
@Suppress("unused")
class configKoin : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@configKoin)
            // Load modules
            modules(listOf(reposatoryModule, viewModelModule))
        }
    }
}