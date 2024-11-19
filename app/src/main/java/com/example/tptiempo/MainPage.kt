package com.example.tptiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tptiempo.router.Enrutador
import com.example.tptiempo.router.Ruta
import com.example.tptiempo.presentacion.ciudades.CiudadesPage
import com.example.tptiempo.presentacion.clima.ClimaPage


class MainPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val router = Enrutador(navController)

            NavHost(navController = navController, startDestination = Ruta.Ciudades.id) {
                composable(Ruta.Ciudades.id) {
                    CiudadesPage(navController)
                }
                composable(Ruta.CLIMA_ROUTE + "?lat={lat}&lon={lon}&nombre={nombre}") { backStackEntry ->
                    val lat = backStackEntry.arguments?.getString("lat")?.toFloatOrNull() ?: 0f
                    val lon = backStackEntry.arguments?.getString("lon")?.toFloatOrNull() ?: 0f
                    val nombre = backStackEntry.arguments?.getString("nombre") ?: ""

                    ClimaPage(navController, lat, lon, nombre)
                }
            }
        }
    }
}









