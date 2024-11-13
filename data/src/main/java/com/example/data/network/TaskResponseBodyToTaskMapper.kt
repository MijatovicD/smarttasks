package com.example.data.network

import com.example.data.model.TaskResponseBody
import com.example.domain.mapper.Mapper
import com.example.domain.model.Task
import kotlinx.coroutines.CoroutineDispatcher

class TaskResponseBodyToTaskMapper(
    coroutineDispatcher: CoroutineDispatcher
) : Mapper<List<TaskResponseBody>, List<Task>>(coroutineDispatcher) {

    override suspend fun List<TaskResponseBody>.toMappedEntity(): List<Task> =
        map { task ->
            Task(
                id = task.id,
                title = task.title,
                description = task.description,
                dueDate = task.dueDate,
                priority = task.priority,
                targetDate = task.targetDate,
            )
        }

}