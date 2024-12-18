package com.example.tptiempo.presentacion.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.repository.modelos.ListForecast
import com.example.tptiempo.router.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class ForecastViewModel(
    private val repositorio: Repositorio,
    private val router: Router,
     val nombre: String
) : ViewModel() {
    private val _estado = MutableStateFlow(ForecastEstado())
    val estado: StateFlow<ForecastEstado> = _estado

    init {
        traerPronostico(nombre)
    }

    fun enviarIntencion(intencion: ForecastIntencion) {
        when (intencion) {
            is ForecastIntencion.TraerPronostico -> traerPronostico(intencion.ciudad)
        }
    }

    private fun traerPronostico(ciudad: String) {
        viewModelScope.launch {
            _estado.value = ForecastEstado(isLoading = true)

            try {
                val forecast = repositorio.traerPronostico(ciudad)
                _estado.value = ForecastEstado(forecast = forecast)
            } catch (e: Exception) {
                _estado.value = ForecastEstado(error = e.message)
            }
        }
    }
}




