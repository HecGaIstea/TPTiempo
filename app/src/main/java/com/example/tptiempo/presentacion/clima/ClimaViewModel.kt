package com.example.tptiempo.presentacion.clima


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.router.Router
import kotlinx.coroutines.launch


class ClimaViewModel(
    val respositorio: Repositorio,
    val router: Router,
    val lat : Float,
    val lon : Float,
    val nombre: String
) : ViewModel() {

    var uiState by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion){
        when(intencion){
            ClimaIntencion.actualizarClima -> traerClima()
        }
    }

    fun traerClima() {
        uiState = ClimaEstado.Cargando
        viewModelScope.launch {
            try{
                val clima = respositorio.traerClima(lat = lat, lon = lon)
                uiState = ClimaEstado.Exitoso(
                    ciudad = clima.name ,
                    temperatura = clima.main.temp,
                    descripcion = clima.weather.first().description,
                    st = clima.main.feels_like,
                )
            } catch (exception: Exception){
                uiState = ClimaEstado.Error(exception.localizedMessage ?: "error desconocido")
            }
        }
    }

}



