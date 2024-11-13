package com.example.smarttasks.di

import com.example.smarttasks.TaskToTaskUiModelMapper
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val mapperModule = module {
    factory { TaskToTaskUiModelMapper(Dispatchers.IO) }
}