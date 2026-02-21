package com.example.asthacare.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
            .padding(24.dp)
    ) {

        Text("Detect air health risk from location")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) return@Button

            loading = true

            location.getLocation { lat, lon ->
                println("LAT=$lat LON=$lon")
                CoroutineScope(Dispatchers.IO).launch {

                    val risk = repo.predict(lat, lon)

                    withContext(Dispatchers.Main) {
                        loading = false
                        navController.navigate("${Routes.RESULT}/$risk")
                    }
                }
            }

        }) {
            Text("Auto Detect")
        }

        if (loading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}