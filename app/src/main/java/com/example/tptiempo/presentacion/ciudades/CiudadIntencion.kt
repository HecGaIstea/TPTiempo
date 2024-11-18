package com.example.tptiempo.presentacion.ciudades

sealed class CiudadIntencion {
    data class BuscarCiudad(val ciudad: String) : CiudadIntencion()
}
