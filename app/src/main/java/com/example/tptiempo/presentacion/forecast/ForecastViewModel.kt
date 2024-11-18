package com.example.tptiempo.presentacion.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.repository.modelos.ListForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForecastViewModel(private val repositorio: Repositorio) : ViewModel() {

    private val _estado = MutableStateFlow(ForecastEstado())
    val estado: StateFlow<ForecastEstado> get() = _estado

    fun enviarIntencion(intencion: ForecastIntencion) {
        when (intencion) {
            is ForecastIntencion.TraerPronostico -> traerPronostico(intencion.nombre)
        }
    }

    private fun traerPronostico(nombre: String) {
        viewModelScope.launch {
            _estado.value = ForecastEstado(isLoading = true)
            try {
                val pronostico = repositorio.traerPronostico(nombre)
                _estado.value = ForecastEstado(forecast = pronostico)
            } catch (e: Exception) {
                _estado.value = ForecastEstado(error = "Error al traer el pronóstico")
            }
        }
    }
}
