package com.example.taskmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskmanager.TaskEntity.AddTask
import com.example.taskmanager.data.SqlQuries


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: userViewModel = viewModel()
    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(
                NavigateToRegister = { navController.navigate("register") },
                NavigateToHome = { navController.navigate("Home") },
                NavigateTOLogin = { navController.navigate("Home") }, viewModel = viewModel
            )
        }
        composable("register") {
            RegisterUser(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable("Home") {
            Home(
                NavigateToTask = { navController.navigate("Add") },
                NavigateToProfile = { navController.navigate("Profile") },
                NavigateToEdit = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() },

                viewModel = viewModel,

                )
        }
        composable("Profile") {
            ProfileScreen(
                onLogout =
                    {
                        navController.navigate("LoginScreen")
                        {
                            popUpTo(0) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
            )
        }
        composable("Add") {
            AddTask(onBack = { navController.popBackStack() }, viewModel = viewModel)
        }

        composable(
            "edit/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { entry ->
            val taskId = entry.arguments!!.getInt("taskId")
            val db = SqlQuries(navController.context)
            val taskList = db.getAllTask()
            val task = taskList.find { it.id == taskId }

            /*   val task =
                   viewModel.getall.collectAsState(initial = emptyList()).value.find { it.id == taskId }*/
            task?.let {
                Edit(
                    NavigateToEdit = { navController.popBackStack() },
                    ItemToBeEdit = it,
                    viewModel = viewModel
                )
            }
        }

    }
}
