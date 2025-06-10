package com.example.bandmate.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bandmate.ui.screens.HomeScreen
import com.example.bandmate.ui.screens.RoleSelectionScreen
import com.example.bandmate.ui.screens.SetlistScreen
import com.example.bandmate.ui.screens.SplashScreen
import com.example.bandmate.ui.screens.WelcomeScreen
import com.example.bandmate.viewmodel.MainViewModel
import com.example.bandmate.viewmodel.SetlistViewModel

@Composable
fun AppNavGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("roles") {
            RoleSelectionScreen(navController, mainViewModel)
        }
        composable("home") {
            HomeScreen(viewModel = mainViewModel, navController = navController)
        }
        composable("setlists") {
            val setlistViewModel: SetlistViewModel = viewModel()
            SetlistScreen(navController = navController, viewModel = setlistViewModel)
        }
    }
}