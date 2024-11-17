package com.example.smarttasks.composeble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarttasks.composeble.TaskCard
import com.example.smarttasks.task.TaskListPreviewParameterProvider
import com.example.smarttasks.task.TaskUiEvent
import com.example.smarttasks.task.model.TaskListUiState
import com.example.smarttasks.task.model.TaskUiModel
import com.example.smarttasks.ui.theme.SmartTasksTheme
import java.time.format.DateTimeFormatter

@Composable
fun TaskListScreen(
    uiState: TaskListUiState,
    currentDate: String,
    onClick: (TaskUiEvent) -> (Unit),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFDE61))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onClick(TaskUiEvent.PreviousDay) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous Day"
                )
            }

            Text(
                text = currentDate,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            IconButton(onClick = { onClick(TaskUiEvent.NextDay) }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next Day"
                )
            }
        }

        Text(
            text = "Today",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        when (uiState) {
            TaskListUiState.Loading -> LoadingScreen()
            is TaskListUiState.Complete -> CompleteTaskListState(uiState.uiModel, onClick)
            TaskListUiState.Empty -> EmptyScreen()
            TaskListUiState.Error -> ErrorScreen()
            else -> return
        }
    }
}

@Composable
private fun CompleteTaskListState(tasks: List<TaskUiModel>, onClick: (TaskUiEvent) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks.size) { index ->
            TaskCard(
                task = tasks[index],
                onClick = onClick,
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
        )
    }
}

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Empty task list.")
    }
}

@Composable
fun ErrorScreen() {
    Box {
        Text(text = "There was unexpected error!")
    }
}

@Composable
fun TaskCard(
    task: TaskUiModel,
    onClick: (TaskUiEvent) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6EFDE)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .heightIn(min = 80.dp)
            .clickable(onClick = { onClick(TaskUiEvent.TaskDetail(task.id)) })
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Text(
                text = task.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEF4B5E),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(7.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column {
                    Text(
                        text = "Due date",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = task.dueDate?.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                            ?: "fasd",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEF4B5E)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Days left",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = task.daysLeft,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEF4B5E)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTasksScreenByDate(
    @PreviewParameter(TaskListPreviewParameterProvider::class) taskListCompleteUiState: TaskListUiState.Complete,
) {
    SmartTasksTheme {
        TaskListScreen(
            uiState = taskListCompleteUiState,
            currentDate = "2024-11-12",
            onClick = { },
        )
    }
}

@Preview
@Composable
fun PreviewTaskListScreen(
    @PreviewParameter(TaskListPreviewParameterProvider::class) taskListUiModel: TaskListUiState.Complete,
) {
    SmartTasksTheme {
        CompleteTaskListState(taskListUiModel.uiModel, onClick = { })
    }
}