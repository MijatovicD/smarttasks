package com.example.smarttasks.task

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.smarttasks.task.model.TaskListUiState
import com.example.smarttasks.task.model.TaskUiModel

class TaskListPreviewParameterProvider : PreviewParameterProvider<TaskListUiState> {

    override val values: Sequence<TaskListUiState> =
        sequenceOf(generateTaskListCompleteUiState())

    private fun generateTaskListCompleteUiState(): TaskListUiState.Complete =
        TaskListUiState.Complete(
            uiModel = listOf(
                TaskUiModel(
                    id = "be06c9b6b02a499daa6f4a9bc12d6d43",
                    targetDate = "2024-11-23",
                    dueDate = "2024-11-29",
                    daysLeft = "12",
                    title = "Setup Jenkinsdkajhfkasdughfkasudghfkadjshfgkadjshfkjasdhfkjalsdhfjkasdhfkjasdhfkjlasdhfkjasdhf",
                    description = "Setup Jenkins environment for SomeCoolApp. Feel free to ask Jeff for help (jeffthemighty@example.com)",
                    priority = 1,
                ),
                TaskUiModel(
                    id = "fsadjkfhaskdf",
                    targetDate = "2022-01-14",
                    dueDate = "2024-11-29",
                    daysLeft = "3",
                    title = "Someee testt",
                    description = "Setup Jenkins environment for SomeCoolApp. Feel free to ask Jeff for help (jeffthemighty@example.com)",
                    priority = 4,
                ),
            )
        )
}