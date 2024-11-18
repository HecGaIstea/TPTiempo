package com.example.tptiempo.presentacion.clima

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun ClimaView(climaViewModel: ClimaViewModel = viewModel()) {
    val estado = climaViewModel.estado.collectAsState().value
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        if (estado.isLoading) {
            Text("Cargando...")
        } else if (estado.error != null) {
            Text("Error: ${estado.error}")
        } else if (estado.clima != null) {
            Text("Ciudad: ${estado.clima.name}")
            Text("Temperatura: ${estado.clima.main.temp}°C")
            Text("Condición: ${estado.clima.weather[0].description}")
        }

        Button(onClick = {
            // Aquí debes pasar la latitud y longitud reales
            scope.launch { climaViewModel.enviarIntencion(ClimaIntencion.TraerClima(37.7749f, -122.4194f)) }
        }) {
            Text("Cargar Clima")
        }
    }
}
