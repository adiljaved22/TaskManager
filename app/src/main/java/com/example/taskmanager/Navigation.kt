package com.example.taskmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(
                NavigateToRegister = { navController.navigate("register") },
                NavigateTOLogin = { navController.navigate("Bio") }
            )
        }
        composable("register") {
            RegisterUser(onBack = { navController.popBackStack() })
        }
        composable("Bio") {
           Bio()
        }
    }
}
