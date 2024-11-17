package com.example.smarttasks.task

sealed class TaskUiEvent {

    data object NextDay : TaskUiEvent()

    data object PreviousDay : TaskUiEvent()

    data class TaskDetail(val id: String) : TaskUiEvent()
}