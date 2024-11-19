package com.example.tptiempo.presentacion.forecast


import com.example.tptiempo.repository.modelos.ListForecast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.ui.Alignment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect


@Composable
fun ForecastView(
    modifier: Modifier = Modifier,
    state : ForecastEstado,
    onAction: (ForecastIntencion)->Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(ForecastIntencion.actualizarClima)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state){
            is ForecastEstado.Error -> ErrorView(mensaje = state.mensaje)
            is ForecastEstado.Exitoso -> ForecastView(state.climas)
            ForecastEstado.Vacio -> LoadingView()
            ForecastEstado.Cargando -> EmptyView()
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun EmptyView(){
    Text(text = "No hay nada que mostrar")
}

@Composable
fun LoadingView(){
    Text(text = "Cargando")
}

@Composable
fun ErrorView(mensaje: String){
    Text(text = mensaje)
}

@Composable
fun ForecastView(climas: List<ListForecast>){
    LazyColumn {
        items(items = climas) {
            Card() {
                Text(text = "${it.main.temp}")
            }
        }
    }
}








