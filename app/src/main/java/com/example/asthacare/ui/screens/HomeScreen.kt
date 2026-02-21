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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.asthacare.data.local.PredictionStore
import com.example.asthacare.navigation.Routes
import com.example.asthacare.data.repository.RiskRepository
import com.example.asthacare.utils.LocationHelper
import com.example.asthacare.data.model.PredictionResult

@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current
    val repo = remember { RiskRepository(context) }
    val locationHelper = remember { LocationHelper(context) }
    val store = remember { PredictionStore(context) }
    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(false) }
    var result by remember {
        mutableStateOf(store.load())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "AsthaCare",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(20.dp))


        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {

                if (result != null) {

                    Text("Current Conditions")
                    Spacer(Modifier.height(8.dp))

                    Text("PM2.5: ${result?.pm25}")
                    Text("Temperature: ${result?.temp} °C")
                    Text("Humidity: ${result?.humidity} %")

                } else {
                    Text("Tap below to fetch live air data")
                }
            }
        }

        Spacer(Modifier.height(20.dp))


        result?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {

                    Text(
                        "Suggestion",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(getAdvice(it.risk))
                }
            }

            Spacer(Modifier.height(20.dp))
        }


        Button(
            onClick = {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) return@Button

                loading = true

                locationHelper.getLocation { lat, lon ->

                    scope.launch(Dispatchers.IO) {

                        val prediction = repo.predict(lat, lon)

                        withContext(Dispatchers.Main) {
                            loading = false
                            result = prediction
                            store.save(prediction)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Health Risk Report")
        }

        if (loading) {
            Spacer(Modifier.height(12.dp))
            CircularProgressIndicator()
        }
        result?.let {
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("prediction", it)

                    navController.navigate(Routes.RESULT)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Detailed Report")
            }
        }
    }
}