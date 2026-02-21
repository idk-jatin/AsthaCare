package com.example.asthacare.ml

import kotlin.math.abs

object FeatureEngineer {

    fun computeFeatures(
        pm25: Float,
        pm10: Float,
        no2: Float,
        so2: Float,
        o3: Float,
        temperature: Float,
        humidity: Float,
        wind: Float
    ): FloatArray {

        // approximate AQI from PM2.5 (same as earlier discussion)
        val aqi = pm25 * 2f

        val aqiCat = when {
            aqi <= 50f -> 0f
            aqi <= 100f -> 1f
            aqi <= 200f -> 2f
            aqi <= 300f -> 3f
            else -> 4f
        }

        val pollutionIndex =
            (pm25 + pm10 + no2 + so2 + o3) / 5f

        val weatherStress =
            abs(temperature - 22f) +
                    abs(humidity - 50f)

        return floatArrayOf(
            aqi,
            pm10,
            pm25,
            no2,
            so2,
            o3,
            temperature,
            humidity,
            wind,
            aqiCat,
            pollutionIndex,
            weatherStress
        )
    }
}