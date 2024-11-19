package com.example.tptiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tptiempo.router.Ruta
import com.example.tptiempo.presentacion.ciudades.CiudadesPage
import com.example.tptiempo.presentacion.clima.ClimaPage
import androidx.navigation.NavType
import androidx.navigation.navArgument


class MainPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainPageContent()
        }
    }
}

@Composable
fun MainPageContent() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Ruta.Ciudades.id
    ) {
        composable(
            route = Ruta.Ciudades.id
        ) {
            CiudadesPage(navController)
        }
        composable(
            route = "${Ruta.CLIMA_ROUTE}?lat={lat}&lon={lon}&nombre={nombre}",
            arguments = listOf(
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lon") { type = NavType.FloatType },
                navArgument("nombre") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getFloat("lat") ?: 0.0f
            val lon = backStackEntry.arguments?.getFloat("lon") ?: 0.0f
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            ClimaPage(navController, lat, lon, nombre)
        }
    }
}










