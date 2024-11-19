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
    CiudadesView(
        ciudadViewModel = viewModel,
        onCitySelected = { ciudadName ->
            viewModel.enviarIntencion(CiudadIntencion.BuscarCiudad(ciudadName))
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
                        onClick = { onCitySelected(ciudad.name) },
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

