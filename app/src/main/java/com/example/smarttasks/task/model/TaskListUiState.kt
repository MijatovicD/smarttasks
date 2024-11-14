package com.example.smarttasks.task.model

sealed class TaskListUiState {

    data object Loading : TaskListUiState()

    data class Complete(val uiModel: List<TaskUiModel>) : TaskListUiState()

    data object Error : TaskListUiState()

    data object Empty : TaskListUiState()
}