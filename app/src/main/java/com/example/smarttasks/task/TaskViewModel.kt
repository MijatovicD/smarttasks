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

class TaskViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val taskToTaskUiModelMapper: TaskToTaskUiModelMapper,
) : ViewModel() {

    private val mutableStateTaskListUiState: MutableStateFlow<TaskListUiState> =
        MutableStateFlow(TaskListUiState.Loading)

    val taskListUiStateFlow: StateFlow<TaskListUiState> =
        mutableStateTaskListUiState.asStateFlow()

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
        }.onFailure {
            Log.d("00>", "Couldn't map to task ui model.")
        }.getOrThrow()
}