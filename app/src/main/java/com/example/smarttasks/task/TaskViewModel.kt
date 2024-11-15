package com.example.smarttasks.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.GetTasksUseCase
import com.example.smarttasks.TaskToTaskUiModelMapper
import com.example.smarttasks.task.model.TaskListUiState
import com.example.smarttasks.task.model.TaskUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val taskToTaskUiModelMapper: TaskToTaskUiModelMapper,
) : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private val mutableStateTaskListUiState: MutableStateFlow<TaskListUiState> =
        MutableStateFlow(TaskListUiState.Loading)

    val taskListUiStateFlow: StateFlow<TaskListUiState> =
        mutableStateTaskListUiState.asStateFlow()

    private var cacheTaskList: List<TaskUiModel> = listOf()

    private val mutableCurrentDateFlow = MutableStateFlow(LocalDate.now().format(dateFormatter))
    val currentDateFlow: StateFlow<String> = mutableCurrentDateFlow

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            getTasksUseCase.runCatching {
                execute(Unit)
            }.mapCatching { tasks ->
                if (tasks.isEmpty()) TaskListUiState.Empty
                else TaskListUiState.Complete(tasks.toUiModel())
            }.getOrThrow()
                .also { uiState ->
                    mutableStateTaskListUiState.update { uiState }
                }
        }
    }

    private suspend fun List<Task>.toUiModel(): List<TaskUiModel> =
        taskToTaskUiModelMapper.runCatching {
            mapList(this@toUiModel)
        }.onSuccess { taskList ->
            cacheTaskList = taskList
            taskList.sortedWith(compareBy<TaskUiModel> { it.priority }
                .thenBy { uiModel ->
                    uiModel.dueDate?.let { LocalDate.parse(it, dateFormatter) }
                }
            )
        }.onFailure {
            Log.d("00>", "Couldn't map to task ui model.")
        }.getOrThrow()

    fun goToPreviousOrNextDay(isNextDay: Boolean) {
        mutableCurrentDateFlow.value =
            if (isNextDay) LocalDate.parse(mutableCurrentDateFlow.value).plusDays(1).toString()
            else LocalDate.parse(mutableCurrentDateFlow.value).minusDays(1).toString()

        val tasksByDate =
            cacheTaskList.filter { task -> task.targetDate == mutableCurrentDateFlow.value }

        mutableStateTaskListUiState.update {
            if (tasksByDate.isNotEmpty()) {
                TaskListUiState.Complete(tasksByDate)
            } else {
                TaskListUiState.Empty
            }
        }
    }
}