package com.example.taskmanager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.TaskEntity.AddTask
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val userViewModel: userViewModel = viewModel()
    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(
                NavigateToRegister = { navController.navigate("register") },
                NavigateToHome = {navController.navigate("Home")},
                NavigateTOLogin = { navController.navigate("Home") }, viewModel = userViewModel
            )
        }
        composable("register") {
            RegisterUser(viewModel = userViewModel, onBack = { navController.popBackStack() })
        }
        composable("Home") {
            Home(
                NavigateToTask = { navController.navigate("Add") },
                NavigateToProfile = { navController.navigate("Profile") },
                 NavigateToEdit = {navController.navigate("edit")},
                viewModel = userViewModel
            )
        }
        composable("Profile") {
            ProfileScreen(
                onLogout =
                    { navController.navigate("LoginScreen")
                    {
                        popUpTo(0){
                        inclusive=false
                        }
                      launchSingleTop=true
                    }
                    }
            )
        }
        composable("Add") {
            AddTask(onBack = { navController.popBackStack() }, viewModel = userViewModel)
        }

        composable("edit/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toInt() ?: 0
            val task = userViewModel.getall.collectAsState(initial = emptyList()).value
                .firstOrNull { it.id == taskId }

            task?.let {
                Edit(
                    ItemToBeEdit = it,
                    NavigateToEdit = { navController.popBackStack() },
                    viewModel = userViewModel
                )
            }
        }

    }
}