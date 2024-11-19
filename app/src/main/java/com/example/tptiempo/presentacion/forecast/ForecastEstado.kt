package com.example.tptiempo.presentacion.forecast

import com.example.tptiempo.repository.modelos.ListForecast



data class ForecastEstado(
    val forecast: List<ListForecast> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

