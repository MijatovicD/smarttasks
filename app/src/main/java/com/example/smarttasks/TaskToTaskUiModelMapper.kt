package com.example.smarttasks

import com.example.domain.mapper.Mapper
import com.example.domain.model.Task
import com.example.smarttasks.task.model.TaskUiModel
import kotlinx.coroutines.CoroutineDispatcher

class TaskToTaskUiModelMapper(defaultDispatcher: CoroutineDispatcher) :
    Mapper<Task, TaskUiModel>(defaultDispatcher) {

    override suspend fun Task.toMappedEntity(): TaskUiModel =
        TaskUiModel(
            id = id,
            title = title,
            description = description,
            dueDate = dueDate,
            priority = priority,
            targetDate = targetDate,
        )
}