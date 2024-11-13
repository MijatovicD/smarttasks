package com.example.smarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.smarttasks.task.model.TaskUIState
import com.example.smarttasks.ui.theme.SmartTasksTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.model.Task
import com.example.smarttasks.task.model.TaskUiModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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
fun TaskListScreen(uiState: TaskUIState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFEB3B))
    ) {

        Text(
            text = "Today",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        when (uiState) {
            is TaskUIState.Complete ->
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.uiModel.size) { index ->
                        TaskCard(uiModel = uiState.uiModel[index])
                    }
                }
            else -> return
        }
    }
}

@Composable
fun TaskCard(uiModel: TaskUiModel) {

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = uiModel.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB71C1C)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Due date",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = uiModel.dueDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB71C1C)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Days left",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = uiModel.dueDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB71C1C)
                    )
                }
            }
        }
    }
}
