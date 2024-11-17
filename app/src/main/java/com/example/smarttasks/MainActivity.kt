package com.example.smarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.smarttasks.task.TaskViewModel
import com.example.smarttasks.ui.theme.SmartTasksTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smarttasks.composeble.TaskDetailScreen
import com.example.smarttasks.composeble.TaskListScreen
import com.example.smarttasks.task.TaskUiEvent

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by taskViewModel.taskListUiStateFlow.collectAsState()
            val currentDate by taskViewModel.currentDateFlow.collectAsState()
            val navController = rememberNavController()

            SmartTasksTheme {
                NavHost(navController = navController, startDestination = "taskList") {
                    composable("taskList") {
                        TaskListScreen(
                            uiState = uiState,
                            currentDate = currentDate,
                            onClick = { uiEvent -> uiEvent.resolve(navController) }
                        )
                    }
                    composable(
                        "taskDetail/{taskId}",
                        arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId").toString()
                        val uiModel = taskViewModel.findTaskById(taskId)
                        TaskDetailScreen(
                            onResolveClick = { /* Handle resolve logic */ },
                            onCannotResolveClick = { /* Handle can't resolve logic */ },
                            onBackClick = { navController.popBackStack() },
                            task = uiModel,
                        )
                    }
                }
            }
        }
    }

    private fun TaskUiEvent.resolve(navController: NavController) {
        when (this) {
            TaskUiEvent.PreviousDay -> taskViewModel.goToPreviousOrNextDay(false)
            TaskUiEvent.NextDay -> taskViewModel.goToPreviousOrNextDay(true)
            is TaskUiEvent.TaskDetail -> navController.navigate("taskDetail/$id")
        }
    }
}
