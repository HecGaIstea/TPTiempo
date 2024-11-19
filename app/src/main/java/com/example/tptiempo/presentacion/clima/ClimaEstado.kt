package com.example.tptiempo.presentacion.clima

import com.example.tptiempo.repository.modelos.Clima



data class ClimaEstado(
    val clima: Clima? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


