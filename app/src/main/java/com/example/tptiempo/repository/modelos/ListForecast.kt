package com.example.tptiempo.repository.modelos


import kotlinx.serialization.Serializable

@Serializable
data class ListForecast(
    val dt: Long,
    val main: MainForecast,
    val weather: List<Weather>
)

@Serializable
data class MainForecast(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val sea_level: Long,
    val grnd_level: Long,
    val humidity: Long,
    val temp_kf: Double,
)