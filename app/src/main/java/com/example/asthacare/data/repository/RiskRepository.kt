package com.example.asthacare.data.repository
import com.example.asthacare.data.model.PredictionResult
import android.content.Context
import com.example.asthacare.ml.FeatureEngineer
import com.example.asthacare.ml.RiskPredictor

class RiskRepository(context: Context) {

    private val predictor = RiskPredictor(context)

    suspend fun predict(lat: Double, lon: Double): PredictionResult {

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

        val features = FeatureEngineer.computeFeatures(
            pm25, pm10, no2, so2, o3,
            temp, hum, wind
        )

        val result = predictor.predict(features)?.toInt()

        val risk = when (result) {
            0 -> "Very High"
            1 -> "High"
            2 -> "Moderate"
            3 -> "Low"
            4 -> "Very Low"
            else -> "Unknown"
        }

        val pollutionIndex =
            (pm25 + pm10 + no2 + so2 + o3) / 5f

        val weatherStress =
            kotlin.math.abs(temp - 22f) +
                    kotlin.math.abs(hum - 50f)

        return PredictionResult(
            risk,
            pm25,
            pm10,
            temp,
            hum,
            wind,
            pollutionIndex,
            weatherStress
        )
    }
}