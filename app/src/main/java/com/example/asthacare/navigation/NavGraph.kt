package com.example.asthacare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.asthacare.ui.screens.HomeScreen
import com.example.asthacare.ui.screens.InputScreen
import com.example.asthacare.ui.screens.ResultScreen

object Routes {
    const val HOME = "home"
    const val INPUT = "input"
    const val RESULT = "result"
}

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.INPUT) {
            InputScreen(navController)
        }

        composable("${Routes.RESULT}/{risk}") { backStackEntry ->
            val risk = backStackEntry.arguments?.getString("risk") ?: "Unknown"
            ResultScreen(navController, risk)
        }
    }
}