package com.example.tptiempo.router

import androidx.navigation.NavHostController




class Enrutador(
    private val navHostController: NavHostController
) : Router {
    override fun navegar(ruta: Ruta) {
        when (ruta) {
            is Ruta.Ciudades -> navHostController.navigate(ruta.id)
            is Ruta.Clima -> {
                val route = "${ruta.id}?lat=${ruta.lat}&lon=${ruta.lon}&nombre=${ruta.nombre}"
                navHostController.navigate(route)
            }
        }
    }
}






