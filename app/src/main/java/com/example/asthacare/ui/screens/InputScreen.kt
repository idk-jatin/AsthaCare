package com.example.asthacare.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.asthacare.navigation.Routes

@Composable
fun InputScreen(navController: NavHostController) {

    var aqi by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var windSpeed by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("Enter Environmental Data", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = aqi,
            onValueChange = { aqi = it },
            label = { Text("AQI") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = temperature,
            onValueChange = { temperature = it },
            label = { Text("Temperature (°C)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = humidity,
            onValueChange = { humidity = it },
            label = { Text("Humidity (%)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = windSpeed,
            onValueChange = { windSpeed = it },
            label = { Text("Wind Speed (m/s)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val risk = predictRisk(aqi)

                navController.navigate("${Routes.RESULT}/$risk")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Predict Risk")
        }
    }
}

private fun predictRisk(aqiInput: String): String {

    val aqi = aqiInput.toIntOrNull() ?: return "Unknown"

    return when {
        aqi > 200 -> "High"
        aqi > 100 -> "Moderate"
        else -> "Low"
    }
}