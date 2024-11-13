package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.TaskRepository

class GetTasksUseCase(
    private val taskRepository: TaskRepository,
): UseCase<Unit, List<Task>> {

    override suspend fun execute(parameter: Unit): List<Task> =
        taskRepository.getTasks()
}