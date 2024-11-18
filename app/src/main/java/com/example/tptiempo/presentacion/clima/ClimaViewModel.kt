package com.example.tptiempo.presentacion.clima


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tptiempo.repository.Repositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ClimaViewModel(private val repositorio: Repositorio, private val lat: Double, private val lon: Double) : ViewModel() {

    private val _estado = MutableStateFlow(ClimaEstado())
    val estado: StateFlow<ClimaEstado> get() = _estado

    init {
        traerClima()
    }

    fun enviarIntencion(intencion: ClimaIntencion) {
        when (intencion) {
            is ClimaIntencion.TraerClima -> traerClima()
        }
    }

    private fun traerClima() {
        viewModelScope.launch {
            _estado.value = ClimaEstado(isLoading = true)
            try {
                val clima = repositorio.traerClima(lat, lon)
                _estado.value = ClimaEstado(clima = clima)
            } catch (e: Exception) {
                _estado.value = ClimaEstado(error = "Error al traer el clima")
            }
        }
    }
}


