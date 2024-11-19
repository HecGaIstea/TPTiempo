package com.example.tptiempo.presentacion.forecast

import com.example.tptiempo.repository.modelos.ListForecast

sealed class ForecastEstado {
    data class Exitoso(
        val climas: List<ListForecast>,
    ) : ForecastEstado()

    data class Error(
        val mensaje: String = "",
    ) : ForecastEstado()

    data object Vacio : ForecastEstado()
    data object Cargando : ForecastEstado()
}
