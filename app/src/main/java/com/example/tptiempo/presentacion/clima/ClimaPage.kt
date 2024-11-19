package com.example.tptiempo.presentacion.clima


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tptiempo.presentacion.forecast.ForecastViewModel
import com.example.tptiempo.presentacion.forecast.ForecastView
import com.example.tptiempo.presentacion.forecast.ForecastViewModelFactory
import com.example.tptiempo.repository.RepositorioApi
import com.example.tptiempo.router.Enrutador



@Composable
fun ClimaPage(
    navHostController: NavHostController,
    lat: Float,
    lon: Float,
    nombre: String
) {
    val viewModel: ClimaViewModel = viewModel(
        factory = ClimaViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            lat = lat,
            lon = lon,
            nombre = nombre
        )
    )
    val pronosticoViewModel: ForecastViewModel = viewModel(
        factory = ForecastViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            nombre = nombre
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        ClimaView(
            climaViewModel = viewModel,
            onAction = { intencion ->
                viewModel.enviarIntencion(intencion)
            }
        )
        ForecastView(
            forecastViewModel = pronosticoViewModel,
            onAction = { intencion ->
                pronosticoViewModel.enviarIntencion(intencion)
            }
        )
    }
}

