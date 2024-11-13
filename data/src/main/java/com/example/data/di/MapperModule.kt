package com.example.data.di

import com.example.data.network.TaskResponseBodyToTaskMapper
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val mapperModule = module {
    factory { TaskResponseBodyToTaskMapper(Dispatchers.Default) }
}