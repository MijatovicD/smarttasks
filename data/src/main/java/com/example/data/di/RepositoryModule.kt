package com.example.data.di

import com.example.data.TaskRepositoryImpl
import com.example.domain.TaskRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<TaskRepository> {
        TaskRepositoryImpl(
            get(),
            get(),
        )
    }
}