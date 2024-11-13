package com.example.domain.mapper

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class Mapper<in T, out R>(
    private val defaultDispatcher: CoroutineDispatcher,
) {

    abstract suspend fun T.toMappedEntity(): R

    suspend fun map(model: T): R =
        withContext(defaultDispatcher) {
            model.toMappedEntity()
        }

    suspend fun mapList(model: List<T>): List<R> =
        mapCollection(model).toList()

    private suspend fun mapCollection(model: Collection<T>): Collection<R> =
        withContext(defaultDispatcher) {
            model.map { it.toMappedEntity() }
        }
}