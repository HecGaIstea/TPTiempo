package com.example.tptiempo.presentacion.ciudades

import com.example.tptiempo.repository.modelos.Ciudad

sealed class CiudadesIntencion {
    data class Buscar( val nombre:String ) : CiudadesIntencion()
    data class Seleccionar(val ciudad: Ciudad) : CiudadesIntencion()
}
