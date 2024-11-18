package com.example.tptiempo.presentacion.ciudades

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch



@Composable
fun CiudadView(ciudadViewModel: CiudadViewModel = viewModel(), onCitySelected: (String) -> Unit) {
    val estado = ciudadViewModel.estado.collectAsState().value
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        var ciudadText by remember { mutableStateOf("") }

        BasicTextField(
            value = ciudadText,
            onValueChange = { ciudadText = it }
        )

        Button(onClick = {
            scope.launch { ciudadViewModel.enviarIntencion(CiudadIntencion.BuscarCiudad(ciudadText)) }
        }) {
            Text("Buscar Ciudad")
        }

        if (estado.isLoading) {
            Text("Cargando...")
        } else if (estado.error != null) {
            Text("Error: ${estado.error}")
        } else {
            Column {
                estado.ciudades.forEach { ciudad ->
                    Button(onClick = { onCitySelected(ciudad.name) }) {
                        Text("Seleccionar ${ciudad.name}, ${ciudad.country}")
                    }
                }
            }
        }
    }
}

