package com.example.taskmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key.Companion.Home
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
                NavigateTOLogin = { navController.navigate("Home") }
            )
        }
        composable("register") {
            RegisterUser(onBack = { navController.popBackStack() })
        }
        composable("Home") {
          Home(NavigateToHome = {navController.navigate("Add")}, NavigateToProfile = {navController.navigate("Profile")})
        }
        composable("Profile"){
            ProfileScreen(onBack = {navController.popBackStack()})
        }
        composable("Add"){
            AddTask(onBack = {navController.popBackStack()})
        }

    }
}