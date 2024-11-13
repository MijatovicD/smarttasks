package com.example.smarttasks.di

import com.example.smarttasks.task.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TaskViewModel(get(), get()) }
}