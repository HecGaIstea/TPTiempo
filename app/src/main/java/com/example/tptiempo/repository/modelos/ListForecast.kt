package com.example.tptiempo.repository.modelos


import kotlinx.serialization.Serializable

@Serializable
data class ListForecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>
)
