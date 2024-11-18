package com.example.tptiempo.presentacion.forecast


sealed class ForecastIntencion {
    data class TraerPronostico(val nombre: String) : ForecastIntencion()
}
