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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
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

    private val mutableCurrentDateFlow = MutableStateFlow(LocalDate.now())
    val currentDateFlow: StateFlow<LocalDate> = mutableCurrentDateFlow

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
            taskList.sortedWith(
                compareBy<TaskUiModel> { it.priority }.thenBy { LocalDate.parse(it.dueDate, dateFormatter) }
            )
        }.onFailure {
            Log.d("00>", "Couldn't map to task ui model.")
        }.getOrThrow()

    fun goToPreviousOrNextDay(isNextDay: Boolean) {
        viewModelScope.launch {
            mutableCurrentDateFlow.value = if (isNextDay) mutableCurrentDateFlow.value.plusDays(1)
            else mutableCurrentDateFlow.value.minusDays(1)

            val completeState =
                mutableStateTaskListUiState.filterIsInstance<TaskListUiState.Complete>()
            val taskList = completeState.mapNotNull { it.uiModel }
                .map { task -> task.filter { dueDate -> dueDate.equals(mutableCurrentDateFlow.value) } }
            mutableStateTaskListUiState.update { TaskListUiState.Complete(taskList.first()) }
        }
    }
}