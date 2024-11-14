package com.example.smarttasks.task

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.smarttasks.task.model.TaskUIState
import com.example.smarttasks.task.model.TaskUiModel

class TaskListPreviewParameterProvider : PreviewParameterProvider<TaskUIState> {

    override val values: Sequence<TaskUIState> =
        sequenceOf(generateTaskListCompleteUiState())

    private fun generateTaskListCompleteUiState(): TaskUIState.Complete =
        TaskUIState.Complete(
            uiModel = listOf(
                TaskUiModel(
                    id = "be06c9b6b02a499daa6f4a9bc12d6d43",
                    targetDate = "2024-11-23",
                    dueDate = "2024-11-29",
                    title = "Setup Jenkinsdkajhfkasdughfkasudghfkadjshfgkadjshfkjasdhfkjalsdhfjkasdhfkjasdhfkjlasdhfkjasdhf",
                    description = "Setup Jenkins environment for SomeCoolApp. Feel free to ask Jeff for help (jeffthemighty@example.com)",
                    priority = 1,
                )
            )
        )
}