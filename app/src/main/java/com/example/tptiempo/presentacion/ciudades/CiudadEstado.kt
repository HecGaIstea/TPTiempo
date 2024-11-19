package com.example.tptiempo.presentacion.ciudades

import com.example.tptiempo.repository.modelos.Ciudad


data class CiudadEstado(
    val ciudades: List<Ciudad> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)



