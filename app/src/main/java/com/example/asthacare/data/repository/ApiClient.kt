package com.example.asthacare.data.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val airRetrofit =
        Retrofit.Builder()
            .baseUrl("https://air-quality-api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val weatherRetrofit =
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val airApi = airRetrofit.create(AirApi::class.java)
    val weatherApi = weatherRetrofit.create(WeatherApi::class.java)
}