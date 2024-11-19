package com.example.tptiempo.router


import androidx.navigation.NavHostController

interface Router {
    fun navegar(ruta: Ruta)
}

sealed class Ruta(val id: String) {
    object Ciudades : Ruta("ciudades")
    data class Clima(val lat: Float, val lon: Float, val nombre: String) : Ruta("clima")

    companion object {
        const val CLIMA_ROUTE = "clima_route"
    }
}









