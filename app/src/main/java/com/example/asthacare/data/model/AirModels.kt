package com.example.asthacare.data.model

data class AirResponse(val hourly: HourlyAir)

data class HourlyAir(
    val pm10: List<Float>,
    val pm2_5: List<Float>,
    val nitrogen_dioxide: List<Float>,
    val sulphur_dioxide: List<Float>,
    val ozone: List<Float>
)

data class WeatherResponse(val hourly: HourlyWeather)

data class HourlyWeather(
    val temperature_2m: List<Float>,
    val relative_humidity_2m: List<Float>,
    val windspeed_10m: List<Float>
)