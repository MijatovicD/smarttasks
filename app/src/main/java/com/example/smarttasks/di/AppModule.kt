package com.example.smarttasks.di

import com.example.domain.usecase.useCaseModule

val appModule = listOf(
    viewModelModule,
    useCaseModule,
    mapperModule,
)
