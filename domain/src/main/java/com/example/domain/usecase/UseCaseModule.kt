package com.example.domain.usecase

import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTasksUseCase(get()) }
}