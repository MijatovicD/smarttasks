package com.example.di

import com.example.data.di.dataModules
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val dataQualifier = StringQualifier("data")

val dependencyInjectionModule = module {
    single(dataQualifier) { dataModules }
}