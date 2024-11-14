package com.example.smarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.smarttasks.task.TaskViewModel
import com.example.smarttasks.task.model.TaskListUiState
import com.example.smarttasks.ui.theme.SmartTasksTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.smarttasks.task.TaskListPreviewParameterProvider
import com.example.smarttasks.task.model.TaskUiModel
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: TaskViewModel by viewModel()

        setContent {
            val uiState by viewModel.taskListUiStateFlow.collectAsState()

            SmartTasksTheme {
                TaskListScreen(
                    uiState = uiState,
                )
            }
        }
    }
}

@Composable
fun TaskListScreen(uiState: TaskListUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFDE61))
    ) {
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
            is TaskListUiState.Complete -> CompleteTaskListState(uiState.uiModel)
            TaskListUiState.Empty -> EmptyScreen()
            TaskListUiState.Error -> ErrorScreen()
            else -> return
        }
    }
}

@Composable
private fun CompleteTaskListState(tasks: List<TaskUiModel>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks.size) { index ->
            TaskCard(task = tasks[index])
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

    }
}

@Composable
fun ErrorScreen() {
    Box {
        Text(text = "There was unexpected error!")
    }
}

@Composable
fun TaskCard(task: TaskUiModel) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6EFDE)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .heightIn(min = 80.dp)
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
                        text = task.dueDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")),
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
                        text = task.dueDate,
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
fun PreviewTaskListScreen(
    @PreviewParameter(TaskListPreviewParameterProvider::class) taskListUiModel: TaskListUiState.Complete,
) {
    SmartTasksTheme {
        CompleteTaskListState(taskListUiModel.uiModel)
    }
}