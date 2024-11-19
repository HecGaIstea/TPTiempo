package com.example.tptiempo.presentacion

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tptiempo.presentacion.clima.ClimaView
import com.example.tptiempo.presentacion.clima.ClimaViewModel
import com.example.tptiempo.presentacion.clima.ClimaViewModelFactory
import com.example.tptiempo.presentacion.forecast.ForecastView
import com.example.tptiempo.presentacion.forecast.ForecastViewModel
import com.example.tptiempo.presentacion.forecast.ForecastViewModelFactory
import com.example.tptiempo.repository.RepositorioApi
import com.example.tptiempo.router.Enrutador

@Composable
fun ClimaPage(
    navHostController: NavHostController,
    lat : Float,
    lon : Float,
    nombre: String
){
    val viewModel : ClimaViewModel = viewModel(
        factory = ClimaViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            lat = lat,
            lon = lon,
            nombre = nombre
        )
    )
    val pronosticoViewModel : ForecastViewModel = viewModel(
        factory = ForecastViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            nombre = nombre
        )
    )

    Column {
        ClimaView(
            state = viewModel.uiState,
            onAction = { intencion ->
                viewModel.ejecutar(intencion)
            }
        )
        ForecastView(
            state = pronosticoViewModel.uiState,
            onAction = { intencion ->
                pronosticoViewModel.ejecutar(intencion)
            }
        )
    }

}