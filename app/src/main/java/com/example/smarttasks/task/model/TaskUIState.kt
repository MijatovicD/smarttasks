package com.example.smarttasks.task.model

sealed class TaskUIState {

    data object Loading : TaskUIState()

    data class Complete(val uiModel: List<TaskUiModel>) : TaskUIState()

    data object Error : TaskUIState()

    data object Empty : TaskUIState()
}