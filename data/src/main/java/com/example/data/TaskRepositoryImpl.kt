package com.example.data

import com.example.data.network.TaskResponseBodyToTaskMapper
import com.example.data.network.TaskService
import com.example.domain.model.Task
import com.example.domain.TaskRepository

class TaskRepositoryImpl(
    private val taskService: TaskService,
    private val taskResponseBodyToTaskMapper: TaskResponseBodyToTaskMapper,
): TaskRepository {

    override suspend fun getTasks(): List<Task> =
        taskService.runCatching {
            getTasks()
        }.mapCatching { tasks ->
            requireNotNull(tasks.body()) { "Task list is null." }
        }.mapCatching { taskList ->
            taskResponseBodyToTaskMapper.map(taskList.tasks)
        }.getOrThrow()
}