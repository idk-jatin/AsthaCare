package com.example.asthacare.data.model

import java.io.Serializable

data class PredictionResult(
    val risk: String,
    val pm25: Float,
    val pm10: Float,
    val temp: Float,
    val humidity: Float,
    val wind: Float,
    val pollutionIndex: Float,
    val weatherStress: Float
) : Serializable