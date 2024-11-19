package com.example.tptiempo.repository.modelos


import kotlinx.serialization.Serializable

@Serializable
data class Clima(
    val main: Main,
    val weather: List<Weather>,
    val name: String
)

@Serializable
data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Weather(
    val description: String,
    val icon: String
)
