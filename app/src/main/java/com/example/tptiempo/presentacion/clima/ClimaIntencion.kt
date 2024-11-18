package com.example.tptiempo.presentacion.clima

sealed class ClimaIntencion {
    data class TraerClima(val lat: Float, val lon: Float) : ClimaIntencion()
}
