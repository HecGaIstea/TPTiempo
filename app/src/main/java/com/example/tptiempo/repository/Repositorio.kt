package com.example.tptiempo.repository

import com.example.tptiempo.repository.modelos.Ciudad
import com.example.tptiempo.repository.modelos.Clima
import com.example.tptiempo.repository.modelos.ListForecast


interface Repositorio {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>
    suspend fun traerClima(lat: Float, lon: Float): Clima
    suspend fun traerPronostico(nombre: String): List<ListForecast>
}

