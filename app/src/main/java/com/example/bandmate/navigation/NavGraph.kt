package com.example.bandmate.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bandmate.ui.screens.HomeScreen
import com.example.bandmate.ui.screens.RoleSelectionScreen
import com.example.bandmate.ui.screens.SetlistDetailScreen
import com.example.bandmate.ui.screens.SetlistScreen
import com.example.bandmate.ui.screens.SongDetailScreen
import com.example.bandmate.ui.screens.SongViewScreen
import com.example.bandmate.ui.screens.SplashScreen
import com.example.bandmate.ui.screens.WelcomeScreen
import com.example.bandmate.viewmodel.MainViewModel
import com.example.bandmate.viewmodel.SetlistViewModel


@Composable
fun AppNavGraph(navController: NavHostController, mainViewModel: MainViewModel, setlistViewModel: SetlistViewModel = viewModel()) {
    val setlistViewModel: SetlistViewModel = viewModel()
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
            SetlistScreen(navController = navController, viewModel = setlistViewModel)
        }
        composable(
            "setlistDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val setlistId = it.arguments?.getInt("id") ?: -1
            SetlistDetailScreen(navController, setlistViewModel, setlistId)
        }
        composable(
            "songDetail/{songId}",
            arguments = listOf(navArgument("songId") { type = NavType.IntType })
        ) {
            val songId = it.arguments?.getInt("songId") ?: -1
            SongDetailScreen(navController = navController, viewModel = setlistViewModel, songId = songId)
        }
        composable(
            "songView/{songId}",
            arguments = listOf(navArgument("songId") { type = NavType.IntType })
        ) {
            val songId = it.arguments?.getInt("songId") ?: -1
            SongViewScreen(navController = navController, viewModel = setlistViewModel, songId = songId)
        }

    }
}