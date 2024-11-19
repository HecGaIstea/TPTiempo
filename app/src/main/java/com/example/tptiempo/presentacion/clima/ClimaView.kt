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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp




@Composable
fun ClimaView(
    climaViewModel: ClimaViewModel,
    onAction: (ClimaIntencion) -> Unit
) {
    val estado = climaViewModel.estado.collectAsState().value
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(16.dp)
    ) {
        if (estado.isLoading) {
            Text("Cargando...", color = Color.Blue)
        } else if (estado.error != null) {
            Text("Error: ${estado.error}", color = Color.Red)
        } else if (estado.clima != null) {
            Text("Ciudad: ${estado.clima.name}", color = Color.Black, style = TextStyle(fontSize = 20.sp))
            Text("Temperatura: ${estado.clima.main.temp}°C", color = Color.Black, style = TextStyle(fontSize = 18.sp))
            Text("Condición: ${estado.clima.weather[0].description}", color = Color.Gray, style = TextStyle(fontSize = 16.sp))
        }

        Button(
            onClick = {
                scope.launch { onAction(ClimaIntencion.TraerClima(37.7749f, -122.4194f)) }
            },
            modifier = Modifier.padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Cargar Clima", color = Color.White)
        }
    }
}




@Preview
@Composable
fun PreviewClimaView() {
    ClimaView()
}

fun ClimaView() {
    TODO("Not yet implemented")
}






