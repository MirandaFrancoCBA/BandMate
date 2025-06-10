package com.example.bandmate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bandmate.ui.screens.HomeScreen
import com.example.bandmate.ui.screens.RoleSelectionScreen
import com.example.bandmate.ui.screens.WelcomeScreen
import com.example.bandmate.viewmodel.MainViewModel

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("roles") {
            RoleSelectionScreen(navController, viewModel)
        }
        composable("home") {
            HomeScreen(viewModel)
        }
    }
}