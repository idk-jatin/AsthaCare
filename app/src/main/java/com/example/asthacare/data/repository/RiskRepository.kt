package com.example.asthacare.data.repository

import android.content.Context
import com.example.asthacare.ml.FeatureEngineer
import com.example.asthacare.ml.RiskPredictor

class RiskRepository(context: Context) {

    private val predictor = RiskPredictor(context)

    suspend fun predict(lat: Double, lon: Double): String {

        val air = ApiClient.airApi.air(lat, lon)
        val weather = ApiClient.weatherApi.weather(lat, lon)

        val pm25 = air.hourly.pm2_5.first()
        val pm10 = air.hourly.pm10.first()
        val no2 = air.hourly.nitrogen_dioxide.first()
        val so2 = air.hourly.sulphur_dioxide.first()
        val o3 = air.hourly.ozone.first()

        val temp = weather.hourly.temperature_2m.first()
        val hum = weather.hourly.relative_humidity_2m.first()
        val wind = weather.hourly.windspeed_10m.first()
        println("PM25=$pm25 PM10=$pm10 NO2=$no2 SO2=$so2 O3=$o3")
        println("TEMP=$temp HUM=$hum WIND=$wind")
        val features =
            FeatureEngineer.computeFeatures(
                pm25, pm10, no2, so2, o3,
                temp, hum, wind
            )

        val result = predictor.predict(features)?.toInt()

        return when (result) {
            0 -> "Very High"
            1 -> "High"
            2 -> "Moderate"
            3 -> "Low"
            4 -> "Very Low"
            else -> "Unknown"
        }
    }
}