package com.example.asthacare.ui.screens
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.asthacare.data.model.PredictionResult
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState



@Composable
fun ResultScreen(
    navController: NavHostController,
    result: PredictionResult
) {

    val riskColor = when (result.risk) {
        "Very High" -> Color(0xFFD32F2F)
        "High" -> Color(0xFFF57C00)
        "Moderate" -> Color(0xFFFBC02D)
        "Low" -> Color(0xFF388E3C)
        "Very Low" -> Color(0xFF2E7D32)
        else -> Color.Gray
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            "Health Risk Report",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Risk Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    result.risk,
                    style = MaterialTheme.typography.headlineLarge,
                    color = riskColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    getAdvice(result.risk),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            "Current Air Conditions",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                DataRow("PM2.5", "${result.pm25}")
                DataRow("PM10", "${result.pm10}")
                DataRow("Temperature", "${result.temp} °C")
                DataRow("Humidity", "${result.humidity}%")
                DataRow("Wind Speed", "${result.wind} m/s")
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text("Why this risk?", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {

                Text(
                    "Pollution Index: ${"%.1f".format(result.pollutionIndex)}",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    "Average pollutant concentration. " +
                            "Above ~60 generally indicates unhealthy air."
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Weather Stress: ${"%.1f".format(result.weatherStress)}",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    "Measures how far temperature (22°C ideal) " +
                            "and humidity (50% ideal) deviate."
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Dashboard")
        }
    }
}
@Composable
fun DataRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value)
    }
}
fun getAdvice(risk: String): String {
    return when (risk) {

        "Very High" ->
            "Air quality is hazardous.\n" +
                    "• Avoid going outside.\n" +
                    "• Wear N95 mask if necessary.\n" +
                    "• Keep inhaler/meds ready.\n" +
                    "• Use air purifier if possible."

        "High" ->
            "Air quality is unhealthy.\n" +
                    "• Limit outdoor exposure.\n" +
                    "• Avoid heavy exercise outside.\n" +
                    "• Mask recommended.\n" +
                    "• Close windows during peak pollution."

        "Moderate" ->
            "Moderate air quality.\n" +
                    "• Sensitive people be cautious.\n" +
                    "• Avoid long outdoor workouts.\n" +
                    "• Stay hydrated."

        "Low" ->
            "Air quality mostly safe.\n" +
                    "• Normal outdoor activity OK.\n" +
                    "• Still monitor if asthma prone."

        "Very Low" ->
            "Excellent air quality.\n" +
                    "• Safe for outdoor activities.\n" +
                    "• Good time for exercise."

        else ->
            "Insufficient data."
    }
}