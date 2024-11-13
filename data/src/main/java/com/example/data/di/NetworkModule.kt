package com.example.data.di

import com.example.data.network.ServiceProvider
import com.example.data.network.TaskService
import org.koin.dsl.module

val networkModule = module {
    single<TaskService> { ServiceProvider.create().create(TaskService::class.java) }
    single { ServiceProvider.create() }
}