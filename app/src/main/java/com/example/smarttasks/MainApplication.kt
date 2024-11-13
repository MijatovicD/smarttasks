package com.example.smarttasks

import android.app.Application
import com.example.di.dataQualifier
import com.example.di.dependencyInjectionModule
import com.example.smarttasks.di.appModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.android.ext.android.inject

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule.plus(dependencyInjectionModule))
            val dataModules: List<Module> by this@MainApplication.inject(dataQualifier)
            koin.loadModules(dataModules)
        }
    }
}