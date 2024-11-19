package com.example.tptiempo.presentacion.ciudades

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tptiempo.repository.RepositorioApi
import com.example.tptiempo.router.Enrutador
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tptiempo.router.Ruta
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.tptiempo.repository.modelos.Ciudad


@Composable
fun CiudadesPage(
    navHostController: NavHostController
) {
    val viewModel: CiudadViewModel = viewModel(
        factory = CiudadesViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController)
        )
    )


    val estado by viewModel.estado.collectAsState()
    var ciudadSeleccionada by remember { mutableStateOf<Ciudad?>(null) }


    LaunchedEffect(estado.ciudades) {
        ciudadSeleccionada?.let { ciudad ->
            val lat = ciudad.lat
            val lon = ciudad.lon
            navHostController.navigate("${Ruta.CLIMA_ROUTE}?lat=$lat&lon=$lon&nombre=${ciudad.name}")
        }
    }

    CiudadesView(
        ciudadViewModel = viewModel,
        onCitySelected = { ciudadName ->

            viewModel.enviarIntencion(CiudadIntencion.BuscarCiudad(ciudadName))
            ciudadSeleccionada = estado.ciudades.find { it.name == ciudadName }
        }
    )
}



@Composable
fun CiudadesView(
    ciudadViewModel: CiudadViewModel,
    onCitySelected: (String) -> Unit
) {
    val estado = ciudadViewModel.estado.collectAsState().value
    var ciudadText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = ciudadText,
            onValueChange = { ciudadText = it },
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
        )

        Button(
            onClick = {
                onCitySelected(ciudadText)
            },
            modifier = Modifier.padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Buscar Ciudad", color = Color.White)
        }

        if (estado.isLoading) {
            Text("Cargando...", color = Color.Blue)
        } else if (estado.error != null) {
            Text("Error: ${estado.error}", color = Color.Red)
        } else {
            Column {
                estado.ciudades.forEach { ciudad ->
                    Button(
                        onClick = {
                            onCitySelected(ciudad.name)
                        },
                        modifier = Modifier.padding(top = 4.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Seleccionar ${ciudad.name}, ${ciudad.country}", color = Color.White)
                    }
                }
            }
        }
    }
}






