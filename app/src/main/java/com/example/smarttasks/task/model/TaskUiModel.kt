package com.example.smarttasks.task.model

data class TaskUiModel(
    val id: String,
    val targetDate: String,
    val dueDate: String?,
    val daysLeft: String,
    val title: String,
    val description: String,
    val priority: Int?,
)