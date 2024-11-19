package com.example.tptiempo.repository.modelos


import kotlinx.serialization.Serializable

@Serializable
data class ForecastDTO(
    val list: List<ListForecast>,
    val cod: String,
    val message: Long,
    val cnt: Long,
)
