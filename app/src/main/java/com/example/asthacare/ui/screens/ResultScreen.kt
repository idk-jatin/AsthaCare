package com.example.asthacare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ResultScreen(navController: NavHostController, risk: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Predicted Risk Level", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(risk, style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        Text(getAdvice(risk))

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

private fun getAdvice(risk: String): String {
    return when (risk) {
        "Very High" -> "Severe health risk. Avoid outdoor exposure completely."
        "High" -> "Limit outdoor activities. Wear mask if necessary."
        "Moderate" -> "Sensitive individuals should take precautions."
        "Low" -> "Air quality generally safe."
        "Very Low" -> "Excellent air quality."
        else -> "Insufficient data."
    }
}