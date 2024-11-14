package com.example.smarttasks

import com.example.domain.mapper.Mapper
import com.example.domain.model.Task
import com.example.smarttasks.task.model.TaskUiModel
import kotlinx.coroutines.CoroutineDispatcher
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class TaskToTaskUiModelMapper(defaultDispatcher: CoroutineDispatcher) :
    Mapper<Task, TaskUiModel>(defaultDispatcher) {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override suspend fun Task.toMappedEntity(): TaskUiModel =
        TaskUiModel(
            id = id,
            title = title,
            description = description,
            dueDate = dueDate,
            daysLeft = dueDate.daysLeft(),
            priority = priority,
            targetDate = targetDate,
        )

    private fun String.daysLeft(): String =
        LocalDate.parse(this, dateFormatter).let { dueDateParsed ->
            LocalDate.now().run { ChronoUnit.DAYS.between(this, dueDateParsed) }.toString()
        }
}