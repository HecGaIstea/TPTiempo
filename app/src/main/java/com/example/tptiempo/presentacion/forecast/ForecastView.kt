package com.example.tptiempo.presentacion.forecast

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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch

@Composable
fun ForecastGraph(forecast: List<ListForecast>) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
            }
        },
        modifier = Modifier.fillMaxWidth(),
        update = { chart ->
            val entries = forecast.mapIndexed { index, listForecast ->
                Entry(index.toFloat(), listForecast.main.temp_max)
            }
            val dataSet = LineDataSet(entries, "Temperatura Máxima")
            val lineData = LineData(dataSet)
            chart.data = lineData
            chart.invalidate() // refresh
        }
    )
}

@Composable
fun ForecastView(forecastViewModel: ForecastViewModel = viewModel()) {
    val estado = forecastViewModel.estado.collectAsState().value
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        // Llama a ForecastGraph para mostrar el gráfico
        if (estado.isLoading) {
            Text("Cargando...")
        } else if (estado.error != null) {
            Text("Error: ${estado.error}")
        } else if (estado.forecast.isNotEmpty()) {
            ForecastGraph(estado.forecast)
            LazyColumn {
                items(estado.forecast) { item ->
                    Text(text = "Fecha: ${item.dt}")
                    Text(text = "Temperatura: ${item.main.temp}°C")
                    Text(text = "Condición: ${item.weather[0].description}")
                }
            }
        }
    }

    // Aquí se puede iniciar la carga del pronóstico usando el nombre de la ciudad
    scope.launch {
        forecastViewModel.enviarIntencion(ForecastIntencion.TraerPronostico("Buenos Aires"))
    }
}

