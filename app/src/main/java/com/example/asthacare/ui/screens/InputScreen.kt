package com.example.asthacare.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.asthacare.data.repository.RiskRepository
import com.example.asthacare.navigation.Routes
import com.example.asthacare.utils.LocationHelper
import kotlinx.coroutines.*

@Composable
fun InputScreen(navController: NavHostController) {

    val context = LocalContext.current
    val repo = remember { RiskRepository(context) }
    val location = remember { LocationHelper(context) }

    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Air Health Check",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "We use your location to fetch live air quality data " +
                    "and predict respiratory health risk.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) return@Button

                loading = true

                location.getLocation { lat: Double, lon: Double ->

                    CoroutineScope(Dispatchers.IO).launch {

                        val result = repo.predict(lat, lon)

                        withContext(Dispatchers.Main) {

                            loading = false

                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("prediction", result)

                            navController.navigate(Routes.RESULT)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Detect Automatically")
        }

        if (loading) {

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.width(12.dp))
                Text("Fetching air quality data…")
            }
        }
    }
}