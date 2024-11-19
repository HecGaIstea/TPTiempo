package com.example.tptiempo.presentacion.forecast

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tptiempo.repository.modelos.ListForecast
import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.runtime.*


@Composable
fun ForecastGraph(forecast: List<ListForecast>) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(16.dp)
        .background(Color.Yellow, shape = RoundedCornerShape(8.dp))) {
        val spacing = size.width / (forecast.size - 1)
        val maxTemp = forecast.maxOf { it.main.temp_max }
        val minTemp = forecast.minOf { it.main.temp_min }

        drawIntoCanvas { canvas ->
            forecast.forEachIndexed { index, forecast ->
                val x = index * spacing
                val y = size.height - ((forecast.main.temp_max - minTemp) / (maxTemp - minTemp) * size.height)
                drawCircle(
                    color = Color.Blue,
                    radius = 5.dp.toPx(),
                    center = androidx.compose.ui.geometry.Offset(x, y),
                    style = Stroke(width = 3.dp.toPx())
                )
            }
        }
    }
}

@Composable
fun ForecastView(
    forecastViewModel: ForecastViewModel,
    onAction: (ForecastIntencion) -> Unit
) {
    val estado = forecastViewModel.estado.collectAsState().value
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
            .padding(16.dp)
    ) {
        if (estado.isLoading) {
            Text("Cargando...", color = Color.Blue)
        } else if (estado.error != null) {
            Text("Error: ${estado.error}", color = Color.Red)
        } else if (estado.forecast.isNotEmpty()) {
            ForecastGraph(estado.forecast)
            LazyColumn {
                items(estado.forecast) { item ->
                    Text(
                        text = "Fecha: ${item.dt}",
                        style = TextStyle(fontSize = 16.sp, color = Color.Black)
                    )
                    Text(
                        text = "Temperatura: ${item.main.temp}°C",
                        style = TextStyle(fontSize = 14.sp, color = Color.Black)
                    )
                    Text(
                        text = "Condición: ${item.weather[0].description}",
                        style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                    )
                }
            }
        }

        // Logs de depuración
        Text(text = "Estado: ${estado.forecast.size} pronósticos cargados", color = Color.Green)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            onAction(ForecastIntencion.TraerPronostico("Buenos Aires"))
        }
    }
}


@Preview
@Composable
fun PreviewForecastView() {
    ForecastView()
}

fun ForecastView() {
    TODO("Not yet implemented")
}









