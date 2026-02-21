package com.example.asthacare.data.repository

import com.example.asthacare.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface AirApi {

    @GET("v1/air-quality")
    suspend fun air(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly")
        hourly: String =
            "pm10,pm2_5,nitrogen_dioxide,sulphur_dioxide,ozone"
    ): AirResponse
}

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun weather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly")
        hourly: String =
            "temperature_2m,relative_humidity_2m,windspeed_10m"
    ): WeatherResponse
}