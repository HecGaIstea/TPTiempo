package com.example.tptiempo.presentacion.clima


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.router.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch





class ClimaViewModel(
    private val repositorio: Repositorio,
    private val router: Router,
    private val lat: Float,
    private val lon: Float,
    private val nombre: String
) : ViewModel() {
    private val _estado = MutableStateFlow(ClimaEstado())
    val estado: StateFlow<ClimaEstado> = _estado

    fun enviarIntencion(intencion: ClimaIntencion) {
        when (intencion) {
            is ClimaIntencion.TraerClima -> traerClima(intencion.lat, intencion.lon)
        }
    }

    private fun traerClima(lat: Float, lon: Float) {
        viewModelScope.launch {
            _estado.value = ClimaEstado(isLoading = true)

            try {
                val clima = repositorio.traerClima(lat, lon) // Llama al método adecuado en tu Repositorio
                _estado.value = ClimaEstado(clima = clima)
            } catch (e: Exception) {
                _estado.value = ClimaEstado(error = e.message)
            }
        }
    }
}





