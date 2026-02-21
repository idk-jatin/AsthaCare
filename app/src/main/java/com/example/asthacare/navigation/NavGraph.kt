package com.example.asthacare.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.asthacare.data.model.PredictionResult
import com.example.asthacare.ui.screens.*

object Routes {
    const val HOME = "home"
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

        composable(Routes.RESULT) {

            val result =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<PredictionResult>("prediction")

            if (result != null) {
                ResultScreen(navController, result)
            } else {
                Text("No prediction data")
            }
        }
    }
}